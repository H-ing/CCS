namespace py class_sheduling_service
namespace java ClassShedulingService
namespace js ClassShedulingService

enum Subject{
    HISTORY = 0b000001,
    PHYSICAL = 0b000010,
    GEOGRAPHY = 0b000100,
    POLITICAL = 0b001000,
    BIOLOGICAL = 0b010000,
    CHEMISTRY = 0b100000
}

//statusCode 0:正常 -1：异常

/////////分班

struct StageOneResultOfClassStrategy{
    1:list<list<list<i32>>>   walkingclassCombinationSolution //2*3*2的数组 科目组合的结果
    2:list<list<list<i32>>>   walkingclassCombinationSelection    //2*3*2 对应的科目组合
    3:list<list<double>>   walkingclassCombinationStudentAverageNumner //3*4   每个科目班级的平均人数
    4:list<list<i32>>   walkingclassCombinationClassNumber  //3*4   每个科目的班级数量
    5:list<i32>   minClassNumber //4    对应的最少班级数
    6:list<i32> walkingclassSubject //4 对应的科目
}
struct StageTwoResultOfClassStrategy{
    1:list<list<list<list<i32>>>>   walkingclassHPSolution  //2*2*3*2   历史/物理 相应科目组合的学生人数
    2:list<list<list<i32>>>   walkingclassCombinationSelection    //2*3*2 对应的科目组合
    3:list<list<double>>   walkingclassHPStudentAverageNumber  //2*3   历史/物理   每组学生的平均人数
    4:list<list<i32>>   walkingclassHPClassNumber   //2*3   历史/物理   班级数
    5:list<i32>   minClassNumber  //2   对应的最少班级数
}
struct StageThreeResultOfClassStrategy{
    1:list<list<list<map<i16,i16>>>>    adminclassSolution  //3*2*n 组数*种类数*班级数*{科目组合:人数} 行政班初步结果，分成两种行政班
    2:list<list<double>>   walkingclassStudentAverageNumber  //4*6  组数*科目数*（人数） 每组的每个科目教学班的平均人数
    3:list<i16>    isHPMix //每一组是不是物理历史混合的
    4:list<list<i16>>   walkingClassNumber  //4*6   组数*科目数*（班级数）  
    5:list<i16> subjectList //对应的科目
    6:list<list<map<i16,i16>>>    adminclassTransformSolution //4*n 组数*班级数*{科目组合：人数}    将第一类的行政班拿出来组成第四组
    7:list<list<map<i16,set<i16>>>> adminclassStudentSet    //分配了具体的学生
}
struct StageFourResultOfClassStrategy{
    1:map<i32,list<map<i32,set<i32>>>>   subjectTeachingclass    //{科目:(每科教学班数*{科目组合：<学生集>})} 按学科分类的教学班的科目组合学生集
    2:list<map<i32,list<i32>>>  groupTeachingclassIndex //组*{科目：[教学班索引]} 每一组中每个科目的教学班的索引
    3:list<list<list<list<map<i32,i32>>>>>    groupAdminclassIncludeTeachingIndex //1*组*行政班*[{科目:索引}]   每组中的每个行政班包含的教学班，如果教学班属于走班，则一行会有多个教学班索引
    4:map<string,i32>   subjectDict //{科目名：科目的代码}}
    5:map<i32,list<map<i32,string>>>    subjectTeachingclassTransform   //subjectTeachingclass中‘学生集’转成字符串
}
struct StageFiveResultOfClassStrategy{
    1:map<string,i32> subjectDict  //{学科名称:学科代码}   包含了所有的学科
    2:list<map<i32,list<i32>>> adminclassList  //行政班数*{科目组合代码:学生集}    每个行政班包含的科目组合学生集
    3:list<map<i32,list<i32>>> teachingclassList   //教学班*{科目组合代码:学生集}    每个教学班包含的科目组合学生集
    4:list<list<i32>>   mixteachingclassList   //混合教学班数*所包含的教学班序号   每个混合教学班包含的教学班，教学班序号即teachingclass_list对应的索引
    5:list<map<i32,i32>>  teachingclassIndexList  //教学班数*{科目代码:编号}      每个教学班对应的科目以及编号，通过科目：编号可确定教学班
    6:list<list<i32>>  adminclassMixteachingclassList //行政班数*所包含的混合教学班数     每个行政班所包含的教学班,行政班的索引对应adminclass_list的索引,混合教学班的索引对应mixteachingclass_list
    7:list<list<i32>>   mixteachingclassAdminclassesList  //混合教学班数*所对应的行政班数     每个混合教学班所影响到的行政班
}

struct ClassStrategyRule{
    2:map<i16,i32> subjectTeacherNumber //科目组合对应的老师数量
    3:map<i16,i32> sectionStudentNumber //科目组合对应的学生数量
    4:list<i32> maxAndMinClassStudentNumber //一个普通班级最大/最小人数
    5:i32   runing_time=50  //算法运行时间的度量，值越大，一次运行时间越长，获得好结果的概率提升
}

struct  ClassStrategyModifyResult{
    1:i32   taskId  //任务id
    2:i32   stage   //需要修改的阶段
    5:StageOneResultOfClassStrategy StageOneResultOfClassStrategy   //第一阶段所要修改的结果.....
    6:StageTwoResultOfClassStrategy StageTwoResultOfClassStrategy
    7:StageThreeResultOfClassStrategy StageThreeResultOfClassStrategy
    //8:StageFourResultOfClassStrategy StageFourResultOfClassStrategy
}

struct ResultOfClassStrategyCreateTask{
    1:i32 statusCode    //0-正常 -1-出错
    2:i32 taskId    //任务id
    3:string   message  //返回的消息
}

struct ResultOfClassStrategyDelTask{
    1:i32 statusCode    //0-正常 -1-出错
    2:string   message  //返回的消息
}

struct ResultOfClassStrategyRunTask{
    1:i32 statusCode    //0-运行成功 -1-出错
    2:string message    
}
struct ResultOfClassStrategyGetTasksStatus{
    1:i32   statusCode
    2:map<i32,i16>  tasksStatusMap  //{taskId:status}   每个任务对应的状态  0-完成  1-正在运行  -1-出现了错误
    3:map<i32,i16>  tasksStageMap   //{taksId:stage}    每个任务目前的阶段
    4:string message
}
struct ResultOfClassStrategyGetTaskResult{
    1:i32   statusCode
    2:string    message
    3:i32   taskId
    4:i32   stage
    5:StageOneResultOfClassStrategy StageOneResultOfClassStrategy
    6:StageTwoResultOfClassStrategy StageTwoResultOfClassStrategy
    7:StageThreeResultOfClassStrategy StageThreeResultOfClassStrategy
    8:StageFourResultOfClassStrategy StageFourResultOfClassStrategy
    9:StageFiveResultOfClassStrategy    StageFiveResultOfClassStrategy
}
struct ResultOfClassStrategyModifyTaskResult{
    1:i32   statusCode
    2:string    message
}
struct ResultOfGetClassStrategyRule{
    1:i32   statusCode
    2:string    message
    3:ClassStrategyRule rule
}


//////////排课
struct ClassScheduleRule{
    1:StageFiveResultOfClassStrategy    StageFiveResultOfClassStrategy
    2:map<i32,i16>  subjectSubjectcount     //{科目代码：接次数} 每个科目每周的节次
    3:list<i16> onedaySession   //一天的节次 例子[0,1,2,3,4,5,6]    一天七节
    4:list<map<i32,i16>> teacherList    //教师数*{科目代码：序号}   任课老师列表
    5:list<i32> teachingclassTeacherList    //教学班数*任课老师索引 对应着各自的list：teachingclass_list、teacher_list.后面的索引自行对应
    6:list<i32> classroomList   //教室集
    7:list<i32> teachingclassClassroomList  //每个行政班对应的教室索引
    8:list<list<i32>>   positiveClassSchedule   //一个星期总节数*n  固排课表    把需要固排的“混合教学班索引”放到对应的节次
    9:list<list<i32>>   negativeClassSchedule   //一个星期总节数*n  禁排课表    把需要禁排的“混合教学班索引”放到对应的节次  不用上课的节次放入-1
    10:list<list<i32>>  connectClass    //连排条目数*连排单元 连排单元：  [星期几（0开始）,混合教学班索引,连排数]

    //调试时候设置为1，快速获得结果，平常用默认值即可
    11:i32    populationSize=50
    12:i32    maxPopulationSize=100
}

struct StageOneResultOfClassSchedule{

    4:list<list<i32>>   daysMixteachingclassPool    //天数*n    每天所要上的混合教学班课
    5:list<map<i32,i32>>  daysMixteachingclassSessionNumberList   //天数*{混合教学班:节次数}  每天每个混合教学班要上的节次数
    6:list<map<i32,i32>>  daysTeacherSessionNumberList    //天数*{任课老师:节次数}  每天每位任课老师要上的节次数
    7:list<map<i32,i32>>  daysAdminclassSessionNumberList //天数*{行政班:节次数}  每天每个行政班要上的节次数  用来验证结果的，每个行政班每天都要上满课
}
struct StageTwoResultOfClassSchedule{
    1:list<list<i32>>   sessionClassSchedule  //每周总节次数*混合教学班   在每节中要上课的混合教学班
    2:list<list<list<i32>>>   dayAdminclassClassSchedule  //天数*行政班数*混合教学班    每天，每个行政班，根据节次顺序要上的课。注意这里的混合教学班是有顺序的
}

struct ResultOfClassScheduleSimulateData{
    1:i32 statusCode    //0-正常 -1-出错
    2:string   message  //返回的消息
    3:ClassScheduleRule ClassScheduleRule
}

struct ResultOfClassScheduleCreateTask{
    1:i32 statusCode    //0-正常 -1-出错
    2:i32 taskId    //任务id
    3:string   message  //返回的消息
}
struct ResultOfClassScheduleDelTask{
    1:i32 statusCode    //0-正常 -1-出错
    2:string   message  //返回的消息
}

struct ResultOfClassScheduleRunTask{
    1:i32 statusCode    //0-运行成功 -1-出错
    2:string message    
}

struct ResultOfClassScheduleGetTasksStatus{
    1:i32   statusCode
    2:map<i32,i16>  tasksStatusMap  //{taskId:status}   每个任务对应的状态  0-完成  1-正在运行  -1-出现了错误
    3:map<i32,i16>  tasksStageMap   //{taksId:stage}    每个任务目前的阶段
    4:string message
}

struct ResultOfClassScheduleStopTask{
    1:i32 statusCode    //0-运行成功 -1-出错
    2:string message    
}
struct ResultOfClassScheduleGetTaskResult{
    1:i32   statusCode
    2:string    message
    3:i32   taskId
    4:i32   stage
    5:i32   status
    6:StageOneResultOfClassSchedule StageOneResultOfClassSchedule
    7:StageTwoResultOfClassSchedule StageTwoResultOfClassSchedule
}



service ClassSchedulignService{
    //测试
    bool ping()

    /////////分班
    //创建分班任务
    ResultOfClassStrategyCreateTask createTaskForClassStrategy(1:ClassStrategyRule rule)

    //删除分班任务
    ResultOfClassStrategyDelTask delTaskForClassStrategy(1:i32  taskId)

    //运行分班任务
    ResultOfClassStrategyRunTask runTaskForClassStrategy(1:i32 taskId,2:i32 stage)

    //获取现有任务及其运行情况
    ResultOfClassStrategyGetTasksStatus getTasksStatusForClassStrategy()

    //获取任务结果
    ResultOfClassStrategyGetTaskResult getTaskResultForClassStrategy(1:i32 taskId,2:i32 stage)

    //修改结果
    ResultOfClassStrategyModifyTaskResult modifyTaskResultForClassStrategy(1:ClassStrategyModifyResult classStrategyModifyResult)

    //获取分班任务的现存规则
    ResultOfGetClassStrategyRule getClassStrategyRule(1:i32 taskId)


    /////排课
    //模拟数据，测试用的
    ResultOfClassScheduleSimulateData   simulateDataForClassSchedule()

    //创建排课任务
    ResultOfClassScheduleCreateTask createTaskForClassSchedule(1:ClassScheduleRule rule)

    //删除排课任务
    ResultOfClassScheduleDelTask delTaskForClassSchedule(1:i32  taskId)

    //运行排课任务  异步的形式
    ResultOfClassScheduleRunTask runTaskForClassSchedule(1:i32 taskId,2:i32 stage)

    //获取现有排课任务及其运行情况
    ResultOfClassScheduleGetTasksStatus getTasksStatusForClassSchedule()

    //停止排课任务
    ResultOfClassScheduleStopTask stopTaskForClassSchedule(1:i32 taskId)

    //获取任务结果
    ResultOfClassScheduleGetTaskResult getTaskResultForClassSchedule(1:i32 taskId,2:i32 stage)
}