package com.swsk.data.util.generate.create;

import com.swsk.data.util.generate.db.BeanAttribute;
import com.swsk.data.util.generate.util.Common;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * @author zzy
 * @Date 2018/12/22 4:39 PM
 */
public class CreateDao {
    protected static final Logger logger = LoggerFactory.getLogger(CreateDao.class);

    private String beanFilePath;
    private String systemPath = System.getProperty("user.dir");
    private String beanPackage;
    private String daoPackage;
    private String inBean;
    private List<BeanAttribute> beanAttributes;

    public CreateDao() {

    }

    public CreateDao(String beanFilePath, String inBean) {
        this.beanFilePath = beanFilePath;
        this.inBean = inBean;
        beanPackage = this.beanFilePath.substring(this.beanFilePath.indexOf("java") + 5).replace("/", ".");
        daoPackage = beanPackage.substring(0, beanPackage.lastIndexOf(".")) + ".dao";
    }

    public CreateDao(String beanFilePath) {
        this.beanFilePath = beanFilePath;
        beanPackage = this.beanFilePath.substring(this.beanFilePath.indexOf("java") + 5).replace("/", ".");
        daoPackage = beanPackage.substring(0, beanPackage.lastIndexOf(".")) + ".dao";
    }

    private void readBean() {
        File file = new File(systemPath + "/" + beanFilePath + "/");
        File[] files = file.listFiles();

        if (null != files) {
            try {
                for (File f : files) {
                    if (f.isFile()) {
                        String beanName = f.getName().replaceAll("[.][^.]+$", "");
                        if (!StringUtils.isEmpty(inBean)) {
                            if (!inBean.contains(beanName)) {
                                continue;
                            }
                        }
                        beanAttributes = Common.getBeanAttributes(f.getAbsolutePath());
                        String tableName = getBeanTable(f.getAbsolutePath());
                        String daoInterfaceName = "I" + beanName + "Dao";
                        String daoImlName = beanName + "DaoImpl";
                        this.createFile(systemPath + "/" + beanFilePath.substring(0, beanFilePath.lastIndexOf("/")) + "/dao", daoInterfaceName, this.createDaoInterface(daoInterfaceName, beanName,beanAttributes));
                        this.createFile(systemPath + "/" + beanFilePath.substring(0, beanFilePath.lastIndexOf("/")) + "/dao/impl", daoImlName, this.createDaoImpl(daoImlName, daoInterfaceName, beanName, beanAttributes, tableName));
                    }
                }
                logger.info("dao生成生功!");
            } catch (IOException e) {
                logger.error("dao生成失败!");
                e.printStackTrace();
            }
        }
    }

    private void createFile(String filePath, String daoName, String daoString) throws IOException {
        Common.createFile(filePath + "/" + daoName + ".java",daoString);
    }


    private String createDaoInterface(String daoName, String bean, List<BeanAttribute> beanAttributes) {
        String entity = Common.toFirstWordLow(bean);
        //获取id对象
        String idType = "java.lang.String";

        for (BeanAttribute beanAttribute : beanAttributes) {
            if(beanAttribute.getIsPk()){
                idType = beanAttribute.getType();
            }
        }
        StringBuffer sb = new StringBuffer("package " + daoPackage + ";\n\n");
        sb.append("import " + beanPackage + "." + bean + ";\n");
        sb.append("import " + Common.importPath + "db.IBaseDao;\n");
        sb.append("import ").append(idType).append(";\n");
        sb.append("import java.util.List;\n");
        sb.append(Common.JAVA_HEAD_STR);
        sb.append("public interface " + daoName + " extends IBaseDao<" + bean);
        sb.append(",").append(idType.substring(idType.lastIndexOf(".")+1));
        sb.append( "> {\n");
        sb.append("\n");
        sb.append("    /**\n");
        sb.append("     * 根据属性值查询\n");
        sb.append("     * @param ").append(entity).append("\n");
        sb.append("     * @return ").append("List").append("<").append(bean).append(">\n");
        sb.append("     */\n");
        sb.append("    List<" + bean + "> findList(");
        sb.append(bean).append(" ").append(entity);
        sb.append(");\n");
        sb.append("}");
        return sb.toString();
    }

    private String createDaoImpl(String daoName, String daoInterface, String bean, List<BeanAttribute> beanAttributes, String tableName) {
        String entity = Common.toFirstWordLow(bean);
        //获取id对象
        BeanAttribute idBean = null;
        String idType = "java.lang.String";

        for (BeanAttribute beanAttribute : beanAttributes) {
            if(beanAttribute.getIsPk()){
                idBean = beanAttribute;
                idType = idBean.getType();
            }
        }
        StringBuffer sb = new StringBuffer("package " + daoPackage + ".impl;\n\n");
        sb.append("import " + Common.importPath + "db.Page;\n");
        sb.append("import " + Common.importPath + "db.BaseDao;\n");
        sb.append("import " + beanPackage + "." + bean + ";\n");
        sb.append("import " + daoPackage + "." + daoInterface + ";\n");
        sb.append("import org.apache.commons.lang3.StringUtils;\n");
        sb.append("import java.util.List;\n");
        sb.append("import ").append(idType).append(";\n");
        idType = idType.substring(idType.lastIndexOf(".")+1);
        sb.append("import java.util.ArrayList;\n");
        sb.append("import org.springframework.stereotype.Repository;\n");
        sb.append(Common.JAVA_HEAD_STR);
        sb.append("@Repository\n");
        sb.append("public class " + daoName + " extends BaseDao<" + bean);
        sb.append(",").append(idType);
        sb.append("> implements " + daoInterface + " {\n");
        sb.append("    @Override\n");
        sb.append("    public boolean save(" + bean + " " + entity + ") {\n");
        sb.append("        return super.insertEntity(" + entity + ");\n");
        sb.append("    }\n\n");
        sb.append("    @Override\n");
        sb.append("    public " + bean + " saveAndGet(" + bean + " " + entity + ") {\n");
        sb.append("        return super.insertAndGetEntity(" + entity + ");\n");
        sb.append("    }\n\n");
        sb.append("    @Override\n");
        sb.append("    public boolean update(" + bean + " " + entity + ") {\n");
        sb.append("        return super.updateEntity(" + entity + ");\n");
        sb.append("    }\n\n");
        sb.append("    @Override\n");
        sb.append("    public " + bean + " updateAndGet(" + bean + " " + entity + ") {\n");
        sb.append("        return super.updateAndGetEntity(" + entity + ");\n");
        sb.append("    }\n\n");
        sb.append("    @Override\n");
        sb.append("    public " + bean + " findById("+idType+" id) {\n");

        if(null != idBean){
            sb.append("        return super.findEntityById(id);\n");
        }else{
            sb.append("        return null;\n");
        }
        sb.append("    }\n\n");
        sb.append("    @Override\n");
        sb.append("    public boolean delete(" + bean + " " + entity + ") {\n");
        sb.append("        return super.deleteEntity(" + entity + ");\n");
        sb.append("    }\n\n");
        sb.append("    @Override\n");
        sb.append("    public boolean delById("+idType+" ... ids) {\n");

        if(null != idBean){
            sb.append("        return super.deleteById(ids);\n");
        }else{
            sb.append("        return false;\n");
        }
        sb.append("    }\n\n");
        sb.append("    @Override\n");
        sb.append("    public Page<" + bean + "> findPage(int page, int pageSize) {\n");
        sb.append("        return super.paginate(page,pageSize,null);\n");
        sb.append("    }\n\n");
        sb.append("    @Override\n");
        sb.append("    public List<" + bean + "> findList(").append(bean).append(" ").append(entity);
        sb.append("){\n");
        sb.append("        String wd = \" where\";\n");
        sb.append("        List<Object> params = new ArrayList<>();\n");
        sb.append("        StringBuffer sql = new StringBuffer(\"select * from ").append(tableName).append("\");\n");

        for (int i = 0; i < beanAttributes.size(); i++) {
            if (beanAttributes.get(i).getType().contains("String")) {
                sb.append("        if(!StringUtils.isEmpty(").append(entity).append(".get").append(Common.toFirstWordUpp(beanAttributes.get(i).getName())).append("()").append(")){\n");
            } else {
                sb.append("        if(null != ").append(entity).append(".get").append(Common.toFirstWordUpp(beanAttributes.get(i).getName())).append("()").append("){\n");
            }
            sb.append("            sql.append(wd+\" ").append(beanAttributes.get(i).getColumn()).append(" = ? \");\n");

            if(beanAttributes.size() - i != 1){
                sb.append("            wd = \" and\";\n");
            }
            sb.append("            params.add(").append(entity).append(".get").append(Common.toFirstWordUpp(beanAttributes.get(i).getName())).append("()").append(");\n");
            sb.append("        }\n");
            sb.append("        \n");
        }
        sb.append("        return super.findEntity(sql.toString(), params.toArray());\n");
        sb.append("    }\n\n");
        sb.append("}");
        return sb.toString();
    }


    /**
     * 获取表名
     *
     * @param beanPath
     * @return
     */
    public static String getBeanTable(String beanPath) {
        String tableName = "";
        File file = new File(beanPath);

        try (FileInputStream inputStream = new FileInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains("@Table")) {
                    tableName = line.split("\"")[1];
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return tableName;
    }


    public void generation() {
        this.readBean();
    }

    public static void main(String[] args) {
        /*CreateDao dao = new CreateDao("/main/java/com/system");
        dao.generation();*/
        String ss = "/main/java/com/system";
        System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
    }


}
