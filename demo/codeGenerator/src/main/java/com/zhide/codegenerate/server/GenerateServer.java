package com.zhide.codegenerate.server;


import com.zhide.codegenerate.common.*;
import com.zhide.codegenerate.entity.CodeField;
import com.zhide.codegenerate.entity.CodeTable;
import com.zhide.codegenerate.entity.FieldEntity;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class GenerateServer {
    Configuration cfg = null;

    {
        cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setTemplateLoader(new ClassTemplateLoader(GenerateServer.class, "/templates/ftl"));
        cfg.setDefaultEncoding("UTF-8");
    }

    /**
     * 生成代码
     * @param config 配置信息
     * @return
     */
    public void generateCode(Config config) {
        if(config.getTables()==null || config.getTables().size()==0){
            writeResponse("请至少选择一个表。");
        }
        String serverSuffix=config.getServerSuffix();
        config.setServerSuffix(TableToCodeUtils.firstUp(serverSuffix));
        String entitySuffix=config.getEntitySuffix();
        config.setEntitySuffix(TableToCodeUtils.firstUp(entitySuffix));
        try {
            if(config.getRealPath().trim().equals("/") || config.getRealPath().trim().equals("\\")){
                config.setRealPath("");
            }
            String savePath= config.getRealPath()+"/CodeGenerate";
            config.setRealPath(savePath);
            String savepath=config.getRealPath();
            File dpath=new File(savepath);
            FileUtils.delete(dpath);

            List<CodeTable> codeTables = TableToCodeUtils.tableToCode(config.getTables());
            Template modelTemplate = cfg.getTemplate("Model.java.ftl");
            Template mapperJavaTemplate = cfg.getTemplate("Mapper.java.ftl");
            Template mapperXmlTemplate = cfg.getTemplate("Mapper.xml.ftl");
            Template serviceTemplate = cfg.getTemplate("Service.java.ftl");
            Template serviceImplTemplate = cfg.getTemplate("ServiceImpl.java.ftl");
            Template controllerTemplate = cfg.getTemplate("Controller.java.ftl");
            for (CodeTable tableClass : codeTables) {
                tableClass.setConfig(config);
                List<FieldEntity> tableField = JdbcUtils.getTableField(tableClass.getTableName());
                List<CodeField> codeFields = TableToCodeUtils.fieldToCode(tableField);
                tableClass.setList(codeFields);

                String path = config.getRealPath() + "/" +config.getPackageName().replace(".", "/");
                generate(modelTemplate, tableClass, path + "/"+config.getEntityPath()+"/",config.getEntitySuffix());
                generate(mapperJavaTemplate, tableClass, path + "/mapper/","Mapper");
                generate(mapperXmlTemplate, tableClass, path + "/mapper/","Mapper");
                String serverPath=path + "/"+config.getServerPath()+"/";
                String sSuffix=config.getServerSuffix();
                if(config.getIsInterface()){
                    generate(serviceTemplate, tableClass, path + "/"+config.getServerPath()+"/",config.getServerSuffix());
                    serverPath=path + "/"+config.getServerPath()+"/impl/";
                    sSuffix=config.getServerSuffix()+"Impl";
                }
                generate(serviceImplTemplate, tableClass,serverPath ,sSuffix);
                generate(controllerTemplate, tableClass, path + "/controller/","Controller");
            }
            if(!config.getIsLocal()){
                zipAndDown(config.getRealPath());
            }else{
                writeResponse("代码已生成");
            }
        } catch (Exception e) {
            e.printStackTrace();
            writeResponse("代码生成失败");
        }
    }
    public void generateOne(Config config){
        if(config.getTables()==null || config.getTables().size()!=1){
            writeResponse("只能选择一个表。");
            return;
        }
        String serverSuffix=config.getServerSuffix();
        config.setServerSuffix(TableToCodeUtils.firstUp(serverSuffix));
        String entitySuffix=config.getEntitySuffix();
        config.setEntitySuffix(TableToCodeUtils.firstUp(entitySuffix));
        try {
            if(config.getRealPath().trim().equals("/") || config.getRealPath().trim().equals("\\")){
                config.setRealPath("");
            }

            CodeTable tableClass= TableToCodeUtils.tableToCode(config.getTables()).get(0);
            tableClass.setConfig(config);
            List<FieldEntity> tableField = JdbcUtils.getTableField(tableClass.getTableName());
            List<CodeField> codeFields = TableToCodeUtils.fieldToCode(tableField);
            tableClass.setList(codeFields);
            String code="";

            switch (config.getGenerateOne()){
                case 1:
                    Template modelTemplate = cfg.getTemplate("Model.java.ftl");
                    code=generateStr(modelTemplate, tableClass);
                    break;
                case 2:
                    Template serviceTemplate = cfg.getTemplate("Service.java.ftl");
                    code=generateStr(serviceTemplate, tableClass);
                    break;
                case 3:
                    Template serviceImplTemplate = cfg.getTemplate("ServiceImpl.java.ftl");
                    code=generateStr(serviceImplTemplate, tableClass);
                    break;
                case 4:
                    Template mapperJavaTemplate = cfg.getTemplate("Mapper.java.ftl");
                    code=generateStr(mapperJavaTemplate, tableClass);
                    break;
                case 5:
                    Template mapperXmlTemplate = cfg.getTemplate("Mapper.xml.ftl");
                    code=generateStr(mapperXmlTemplate, tableClass);
                    break;
                case 6:
                    Template controllerTemplate = cfg.getTemplate("Controller.java.ftl");
                    code=generateStr(controllerTemplate, tableClass);
                    break;
            }
            writeResponse(code);
        } catch (Exception e) {
            e.printStackTrace();
            writeResponse("代码生成失败");
        }
    }
    private void writeResponse(RespBean bean){
        try{
            HttpServletResponse response = HttpUtils.getResponse();
            response.reset();
            response.setCharacterEncoding("UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            String str = JsonUtils.toStr(bean);
            outputStream.write(str.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    private void writeResponse(String str){
        try{
            HttpServletResponse response = HttpUtils.getResponse();
            response.setCharacterEncoding("UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(str.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void generate(Template template, CodeTable tableClass, String path,String suffix) throws IOException, TemplateException {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fname=template.getName();
        int s = fname.indexOf(".");
        int e = fname.lastIndexOf(".");
        fname=fname.substring(s,e);
        String fileName = path + "/"+tableClass.getModelName()+suffix + fname;
        FileOutputStream fos = new FileOutputStream(fileName);
        OutputStreamWriter out = new OutputStreamWriter(fos);
        template.process(tableClass,out);
        fos.close();
        out.close();
    }
    private String generateStr(Template template, CodeTable tableClass) throws IOException, TemplateException {
        ByteArrayOutputStream bto=new ByteArrayOutputStream();
        OutputStreamWriter out = new OutputStreamWriter(bto);
        template.process(tableClass,out);
        return bto.toString(StandardCharsets.UTF_8);
    }
    private void zipAndDown(String path) throws IOException {
        if(path.equals("")){
            path="/";
        }
        HttpServletResponse response = HttpUtils.getResponse();
        response.reset();
        response.setContentType("application/octet-stream");
        String name="/CodeGenerate.zip";
        ZipUtils.zipDirectory(path,name);
        File file=new File(name);
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        response.addHeader("Content-Length", "" + file.length());
        BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file));
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.copy(bis,outputStream);
        outputStream.flush();
        bis.close();
        file.delete();
        File dpath=new File(path);
        FileUtils.delete(dpath);
        response.flushBuffer();
    }
}
