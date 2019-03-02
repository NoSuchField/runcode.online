package com.lacecode.util;

import com.lacecode.model.entity.Code;
import com.lacecode.model.entity.Project;
import com.lacecode.model.entity.Team;
import com.lacecode.model.entity.User;
import com.lacecode.data.CodeRepository;
import com.lacecode.data.ProjectRepository;
import com.lacecode.data.TeamRepository;
import com.lacecode.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//初始化数据库(产生测试数据)
@Component
public class Initializr implements CommandLineRunner {
    private final CodeRepository codeRepository;
    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Autowired
    public Initializr(CodeRepository codeRepository, ProjectRepository projectRepository, TeamRepository teamRepository, UserRepository userRepository) {
        this.codeRepository = codeRepository;
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... strings) {
        initTeam();

        initProject();
        initUser();
        initCode();
    }

    private void initTeam() {
        teamRepository.save(new Team("14计科1班"));
        teamRepository.save(new Team("14计科2班"));
        teamRepository.save(new Team("14计科3班"));
    }

    private void initProject() {
        //14计科3班的project
        projectRepository.save(new Project("default_140101", teamRepository.findOne(1)));
        projectRepository.save(new Project("default_140102", teamRepository.findOne(2)));
        projectRepository.save(new Project("[C]打印一个菱形", teamRepository.findOne(3)));

        projectRepository.save(new Project("[Java]100以内素数", teamRepository.findOne(3)));
    }

    private void initUser() {
        Team team;
        String roles ;

        team = teamRepository.findOne(1);
        roles = "User";

        userRepository.save(new User("20142805", team, "徐劲松",roles));
        userRepository.save(new User("20142807", team, "赵伟业",roles));
        userRepository.save(new User("20142810", team, "王健晖",roles));
        userRepository.save(new User("20142817", team, "康佳成",roles));
        userRepository.save(new User("20142819", team, "陈尚平",roles));
        userRepository.save(new User("20142821", team, "郭玲玲",roles));
        userRepository.save(new User("20142827", team, "汤志伟",roles));
        userRepository.save(new User("20142828", team, "徐星星",roles));
        userRepository.save(new User("20142835", team, "杜程程",roles));
        userRepository.save(new User("20142838", team, "刘子藤",roles));
        userRepository.save(new User("20142840", team, "徐慧慧",roles));
        userRepository.save(new User("20142848", team, "唐传龙",roles));
        userRepository.save(new User("20142849", team, "胡景",roles));
        userRepository.save(new User("20142852", team, "王梦璇",roles));
        userRepository.save(new User("20142855", team, "葛骞",roles));
        userRepository.save(new User("20142861", team, "张小敏",roles));
        userRepository.save(new User("20142866", team, "伍小平",roles));
        userRepository.save(new User("20142868", team, "章翔",roles));
        userRepository.save(new User("20142870", team, "徐梦园",roles));
        userRepository.save(new User("20142873", team, "刘忠慧",roles));
        userRepository.save(new User("20142879", team, "陈昊东",roles));
        userRepository.save(new User("20142882", team, "周永泰",roles));
        userRepository.save(new User("20142883", team, "马云龙",roles));
        userRepository.save(new User("20142888", team, "汝昊天",roles));
        userRepository.save(new User("20142890", team, "唐博强",roles));
        userRepository.save(new User("20142893", team, "江子慧",roles));
        userRepository.save(new User("20142894", team, "尉胜男",roles));
        userRepository.save(new User("20142898", team, "刘皖肖",roles));
        userRepository.save(new User("20142900", team, "郑智恒",roles));
        userRepository.save(new User("20142902", team, "刘定生",roles));
        userRepository.save(new User("20142905", team, "徐文阳",roles));
        userRepository.save(new User("20142910", team, "聂玉玲",roles));
        userRepository.save(new User("20142914", team, "左小雪",roles));
        userRepository.save(new User("20142915", team, "陈大宝",roles));
        userRepository.save(new User("20142918", team, "丰婷",roles));
        userRepository.save(new User("20142924", team, "姚绍武",roles));
        userRepository.save(new User("20142925", team, "程前",roles));
        userRepository.save(new User("20142927", team, "倪文",roles));
        userRepository.save(new User("20142928", team, "张海涛",roles));
        userRepository.save(new User("20142931", team, "舒婷婷",roles));
        userRepository.save(new User("20142934", team, "凡梦梦",roles));
        userRepository.save(new User("20142937", team, "杨秋萍",roles));
        userRepository.save(new User("20142941", team, "朱稳稳",roles));
        userRepository.save(new User("20142946", team, "周威",roles));
        userRepository.save(new User("20142948", team, "储小寒",roles));
        userRepository.save(new User("20142951", team, "胡少晖",roles));
        userRepository.save(new User("20144315", team, "马硕",roles));


        team = teamRepository.findOne(2);

        userRepository.save(new User("20142808", team, "卫孝贤",roles));
        userRepository.save(new User("20142811", team, "李玉霞",roles));
        userRepository.save(new User("20142812", team, "石文意",roles));
        userRepository.save(new User("20142813", team, "陈劲宇",roles));
        userRepository.save(new User("20142816", team, "孟玉",roles));
        userRepository.save(new User("20142818", team, "张政",roles));
        userRepository.save(new User("20142820", team, "奚志祥",roles));
        userRepository.save(new User("20142822", team, "韩蕾",roles));
        userRepository.save(new User("20142825", team, "蒲好天",roles));
        userRepository.save(new User("20142829", team, "王泽宇",roles));
        userRepository.save(new User("20142830", team, "郝梦洁",roles));
        userRepository.save(new User("20142832", team, "陈理想",roles));
        userRepository.save(new User("20142843", team, "李道临",roles));
        userRepository.save(new User("20142845", team, "程汝梁",roles));
        userRepository.save(new User("20142851", team, "李应斌",roles));
        userRepository.save(new User("20142853", team, "方福潜",roles));
        userRepository.save(new User("20142858", team, "周余红",roles));
        userRepository.save(new User("20142862", team, "吴铳",roles));
        userRepository.save(new User("20142867", team, "汪臻",roles));
        userRepository.save(new User("20142872", team, "丁健",roles));
        userRepository.save(new User("20142874", team, "关敬婷",roles));
        userRepository.save(new User("20142876", team, "张周宇",roles));
        userRepository.save(new User("20142878", team, "凡光辉",roles));
        userRepository.save(new User("20142884", team, "孙文倩",roles));
        userRepository.save(new User("20142886", team, "赵东旭",roles));
        userRepository.save(new User("20142889", team, "姜文彪",roles));
        userRepository.save(new User("20142895", team, "候梦如",roles));
        userRepository.save(new User("20142896", team, "王培立",roles));
        userRepository.save(new User("20142903", team, "王弄笛",roles));
        userRepository.save(new User("20142904", team, "高仁杰",roles));
        userRepository.save(new User("20142906", team, "范佳佳",roles));
        userRepository.save(new User("20142908", team, "张灿",roles));
        userRepository.save(new User("20142911", team, "许荣",roles));
        userRepository.save(new User("20142916", team, "袁宏飞",roles));
        userRepository.save(new User("20142921", team, "邱远豪",roles));
        userRepository.save(new User("20142923", team, "王明治",roles));
        userRepository.save(new User("20142933", team, "万文娟",roles));
        userRepository.save(new User("20142935", team, "方远远",roles));
        userRepository.save(new User("20142936", team, "张彪",roles));
        userRepository.save(new User("20142939", team, "倪利伟",roles));
        userRepository.save(new User("20142940", team, "高梦梦",roles));
        userRepository.save(new User("20142945", team, "刘康",roles));
        userRepository.save(new User("20142947", team, "石俊",roles));
        userRepository.save(new User("20142949", team, "刘涛",roles));
        userRepository.save(new User("20142950", team, "王涛",roles));
        userRepository.save(new User("20144470", team, "张胜宝",roles));


        team = teamRepository.findOne(3);

        userRepository.save(new User("20142806", team, "王智豪", roles));
        userRepository.save(new User("20142809", team, "许海浪", roles));
        userRepository.save(new User("20142823", team, "魏玉强", roles));
        userRepository.save(new User("20142824", team, "班名洋", roles));
        userRepository.save(new User("20142826", team, "王洪蕊", roles));
        userRepository.save(new User("20142831", team, "李俊茹", roles));
        userRepository.save(new User("20142834", team, "邓小峰", roles));
        userRepository.save(new User("20142836", team, "谢阳琴", roles));
        userRepository.save(new User("20142841", team, "胡丹阳", roles));
        userRepository.save(new User("20142846", team, "高鑫", roles));
        userRepository.save(new User("20142847", team, "徐勇兵", roles));
        userRepository.save(new User("20142850", team, "章园琴", roles));
        userRepository.save(new User("20142854", team, "熊严秋", roles));
        userRepository.save(new User("20142856", team, "陈胡杨", roles));
        userRepository.save(new User("20142859", team, "吴丹红", roles));
        userRepository.save(new User("20142860", team, "王兴", roles));
        userRepository.save(new User("20142864", team, "刘佳君", roles));
        userRepository.save(new User("20142865", team, "朱学锋", roles));
        userRepository.save(new User("20142871", team, "张涛", roles));
        userRepository.save(new User("20142875", team, "汪长春", roles));
        userRepository.save(new User("20142877", team, "蔡颖", roles));
        userRepository.save(new User("20142880", team, "何兆斌", roles));
        userRepository.save(new User("20142885", team, "刘海洋", roles));
        userRepository.save(new User("20142887", team, "张保玲", roles));
        userRepository.save(new User("20142892", team, "殷闵琦", roles));
        userRepository.save(new User("20142897", team, "王静", roles));
        userRepository.save(new User("20142899", team, "王旋", roles));
        userRepository.save(new User("20142901", team, "董丙冰", roles));
        userRepository.save(new User("20142907", team, "黄艳", roles));
        userRepository.save(new User("20142909", team, "李飞", roles));
        userRepository.save(new User("20142912", team, "李刚", roles));
        userRepository.save(new User("20142913", team, "孙杰", roles));
        userRepository.save(new User("20142917", team, "郑伟", roles));
        userRepository.save(new User("20142919", team, "李海燕", roles));
        userRepository.save(new User("20142920", team, "丁瑞军", roles));
        userRepository.save(new User("20142922", team, "李树", roles));
        userRepository.save(new User("20142926", team, "苏芃", roles));
        userRepository.save(new User("20142929", team, "柯健宇", roles));
        userRepository.save(new User("20142930", team, "蔡近远", roles));
        userRepository.save(new User("20142932", team, "蔡永杰", roles));
        userRepository.save(new User("20142938", team, "郑乐", roles));
        userRepository.save(new User("20142942", team, "熊志敏", roles));
        userRepository.save(new User("20142944", team, "张雨星", roles));
        userRepository.save(new User("20144407", team, "陈玮琪", roles));
        userRepository.save(new User("20143461", team, "李新宇", "Administrator"));
    }

    private void initCode() {
        String s = "#include <stdio.h>\n" +
                "int main()\n" +
                "{\n" +
                "    int i,j,k;\n" +
                "    for(i=0;i<=3;i++) {\n" +
                "        for(j=0;j<=2-i;j++) {\n" +
                "            printf(\" \");\n" +
                "        }\n" +
                "        for(k=0;k<=2*i;k++) {\n" +
                "            printf(\"*\");\n" +
                "        }\n" +
                "        printf(\"\\n\");\n" +
                "    }\n" +
                "    for(i=0;i<=2;i++) {\n" +
                "        for(j=0;j<=i;j++) {\n" +
                "            printf(\" \");\n" +
                "        }\n" +
                "        for(k=0;k<=4-2*i;k++) {\n" +
                "            printf(\"*\");\n" +
                "        }\n" +
                "        printf(\"\\n\");\n" +
                "    }\n" +
                "  \n" +
                "}";

        codeRepository.save(new Code("main.c", s, "c", projectRepository.findOne(3), true));
        codeRepository.save(new Code("readme.md", "用C语言打印出一个菱形", "text", projectRepository.findOne(3), false));
        codeRepository.save(new Code("Test.java", "public class Test {  \n" +
                "      \n" +
                "    public static void main(String[] args) {  \n" +
                "        int i, j, k;    \n" +
                "        for (i = 2; i < 100; i++) {    \n" +
                "            k = (int) Math.sqrt(i);    \n" +
                "            for (j = 2; j <= k; j++) {    \n" +
                "    \n" +
                "                if (i % j == 0)    \n" +
                "                    break;    \n" +
                "    \n" +
                "            }    \n" +
                "            if (j > k) {    \n" +
                "                System.out.println(i + \" \");    \n" +
                "            }    \n" +
                "        }    \n" +
                "    }  \n" +
                "}  ", "java", projectRepository.findOne(4), true));
        codeRepository.save(new Code("test.c", "#include<stdio.h>\n" +
                "int main()\n" +
                "{\n" +
                "\tint a,b;\n" +
                "\tscanf(\"%d\",&a);\n" +
                "    printf(\"a = %d\\n\",a);\n" +
                "    scanf(\"%d\",&b);\n" +
                "    printf(\"b = %d\\n\",b);\n" +
                "    printf(\"a = %d\\n\",a+b);\n" +
                "}", "c", projectRepository.findOne(2), true));
    }
}
