package com.rld.iodemo.utils;

import com.rld.iodemo.pojo.Employee;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IOUtil {
    public static  StringBuffer buff=new StringBuffer();
    public static  Set dataSourceSet =new HashSet<Object>();;
    public static  Set employeeSet;
    //public static final String fileOutPath="G:\\test\\output.txt";
    public static final String fileOutPath="./src/main/resources/output.txt";
    public static void run() {
        readFile("comma.txt");
        readFile("pipe.txt");
        readFile("space.txt");
        //System.out.println(buff.toString());

        Iterator iterator = dataSourceSet.iterator();
        employeeSet=new HashSet<Object>();
        Employee employee;
        while (iterator.hasNext()) {
            employee=new Employee();
            String strObj = (String) iterator.next();
            if (strObj.contains("|")) {
                String[] split1 = strObj.split("\\|");
                employee.setFirstName(split1[0].trim());
                employee.setLastName(split1[1].trim());
                employee.setSex(formatSex(split1[3]));
                employee.setFavColor(split1[4].trim());
                employee.setBirthDay(formatDate(split1[5]));
                employee.setDateForCompare(strToDate(formatDate(split1[5])));

                employeeSet.add(employee);
            }else if (strObj.contains(",")){
                String[] split2 = strObj.split(",");
                employee.setFirstName(split2[0].trim());
                employee.setLastName(split2[1].trim());
                employee.setSex(formatSex(split2[2]));
                employee.setFavColor(split2[3].trim());
                employee.setBirthDay(formatDate(split2[4]));
                employee.setDateForCompare(strToDate(formatDate(split2[4])));
                employeeSet.add(employee);
            }else{
                String[] split3= strObj.split(" ");
                employee.setFirstName(split3[0].trim());
                employee.setLastName(split3[1].trim());
                employee.setSex(formatSex(split3[3]));
                employee.setFavColor(split3[5].trim());
                employee.setBirthDay(formatDate(split3[4]));
                employee.setDateForCompare(strToDate(formatDate(split3[4])));
                employeeSet.add(employee);
            }

        }


        List<Employee> list=new ArrayList<>(employeeSet);

        //1、先按性别升序 2、再按姓升序
        list=list.stream().sorted(Comparator.comparing(Employee::getSex).thenComparing(Employee::getFirstName)).collect(Collectors.toList());
        sortedAndWriteList(list,"output1:");

        List<Employee> list2=list;
        //1、先按日期升序 2、再按姓名升序
        list2=list.stream().sorted(Comparator.comparing(Employee::getDateForCompare).thenComparing(Employee::getFirstName)).collect(Collectors.toList());
        sortedAndWriteList(list2,"output2:");

        List<Employee> list3=list2;
        //1、先按姓降序 2、再按日期降序 3、按喜欢的颜色升序
        list3=list.stream().sorted(Comparator.comparing(Employee::getFirstName,Comparator.reverseOrder())
                .thenComparing(Employee::getDateForCompare,Comparator.reverseOrder()).thenComparing(Employee::getFavColor)).collect(Collectors.toList());
        sortedAndWriteList(list3,"output3:");



    }

    /**
     * 给日期指定格式
     * @param date
     * @return
     */
    public static String formatDate(String date){
        if (date.contains("-")) {
            String result = date.replaceAll("-", "/");
            return result.trim();
        }
        return date.trim();
    }

    /**
     * 给性别作匹配处理，如F 对应 Female,M 对应 Male
     * @param sex
     * @return
     */
    public static String formatSex(String sex){
        if ("F".equals(sex.trim())) {
            sex="Female";
            return sex;
        }else if ("M".equals(sex.trim())){
            sex="Male";
            return sex;
        }
        return sex.trim();
    }

    /**
     * 日期按固定格式，将字符串转换成日期，以方便比较
     * @param date
     * @return
     */
    public static Date strToDate(String date){
        try {
            DateFormat fmt =new SimpleDateFormat("M/d/yyyy");
            Date dateResult = fmt.parse(date);
            return dateResult;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把文件内容读取到set中
     * @param fileName
     */
    public static void readFile(String fileName){

        try {
            File file = ResourceUtils.getFile("classpath:"+fileName);
            //File filename = new File("classpath:"+fileName); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            while (line != null) {
                //System.out.println(line);
                buff.append(line+"\n");
                dataSourceSet.add(line);
                line = br.readLine(); // 一次读入一行数据
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将排好序的员工信息列表写入到目标文件
     * @param sourceList
     * @param message
     */
    public static void  sortedAndWriteList(List<Employee> sourceList,String message){
        Iterator sortEmpIterator = sourceList.iterator();
        List<String> targetList=new ArrayList<>();
        while (sortEmpIterator.hasNext()) {
            Employee employee = (Employee) sortEmpIterator.next();
            String employeeInfo=employee.getFirstName()+" "+employee.getLastName()+" "+employee.getSex()+" "+employee.getBirthDay()+" "+employee.getFavColor();
            targetList.add(employeeInfo);
        }
        try {
            writeFileContent(targetList,fileOutPath,message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 负责将内容输出到目标文件
     * @param strings
     * @param path
     * @param message
     * @throws Exception
     */
    public static void writeFileContent(List<String>  strings,String path,String message) throws Exception {
        File file = ResourceUtils.getFile(path);
        //如果没有文件就创建
        if (!file.isFile()) {
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file, true)));
        writer.write(message + "\r\n");
        for (String l:strings){
            writer.write(l + "\r\n");
        }

        writer.write("===================" + "\r\n");
        writer.write("\r\n");
        writer.write("\r\n");
        writer.flush();
        writer.close();


    }


    public static void main(String[] args) {
        run();
    }
}
