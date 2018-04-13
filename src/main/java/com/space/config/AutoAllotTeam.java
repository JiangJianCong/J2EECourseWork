package com.space.config;

import com.space.dao.EduInsPlanRepository;
import com.space.entity.EduInsPlan;
import com.space.entity.Student;
import com.space.util.DateUtil;
import com.space.util.StringUtil;
import org.hibernate.annotations.Source;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
public class AutoAllotTeam {


    @Resource
    private EduInsPlanRepository eduInsPlanRepository;

    /**
     * 分配班级，每天上午10点
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void allotTeam() throws Exception {
        List<EduInsPlan> courses = eduInsPlanRepository.findAll();

        Iterator<EduInsPlan> iterator = courses.iterator();
        while (iterator.hasNext()){
            EduInsPlan course = iterator.next();
            //判断时间
            Date beforeDate = DateUtil.formatString(course.getStartDate(), "yyyy-MM-dd");
            int s = (int) DateUtil.calculateDif(beforeDate);
            int days = s/(60*60*24);

            if (days < 14){
                Set<Student> students = course.getStudent();
                //根据学生id和课程id判断
                System.out.println("学生数："+students.size());
                for (Student student : students) {
                    int stuId = student.getId();
                    String team = eduInsPlanRepository.teamIsNull(stuId, course.getId());
                    if (StringUtil.isEmpty(team)){
                        int weekTimes = course.getWeekTimes();
                        int teamNum = (int)(Math.random()*(weekTimes))+1;
                        eduInsPlanRepository.allotTeam(stuId,course.getId(),Integer.toString(teamNum));
                        System.out.println("学生: "+stuId+"配班成功，课程id："+course.getId() + ", 分配的班级: "+teamNum);
                    }
                }


            }

        }



    }
}
