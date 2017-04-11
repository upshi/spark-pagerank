# 爬取网页并用Spark PageRank计算网页排序

## 接口说明
### 1. 提交任务

- 接口说明 

|名称 |请求方法  |URL  |
| :--------: | :--------: | :-------- |
| 提交任务 | POST  | /api/start |

- 请求参数

| 名称 |类型  |是否必须  |示例值  |更多限制  |说明  |
| :--------: | :--------: | :--------: | :--------: | :-------- | :-------- |
| startUrl |String  |是  |http://www.qq.com  |  | 入口URL |
| total | Integer |是  |10000  |  | 爬取的网页总数  |

- 返回值**data**说明

| 名称 |类型  |示例值  |说明  |
| :--------: | :--------: | :--------: | :-------- |
| taskId |Integer  | 1  | 任务ID  | 

- 返回值**error**说明

| 原因 |说明  |
| :-------- | :------------------------------------- |
| 父级标签不存在 | 请求参数pid不存在|
| 标签名称已经存在 | 标签名称已经存在 |