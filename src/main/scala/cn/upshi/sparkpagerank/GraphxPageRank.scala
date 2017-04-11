package cn.upshi.sparkpagerank

import java.util

import cn.upshi.sparkpagerank.model.PageRankResult
import cn.upshi.sparkpagerank.util.FileUtil
import org.apache.spark.graphx.{GraphLoader, VertexId}
import org.apache.spark.{SparkConf, SparkContext}
import org.springframework.stereotype.Component

import collection.JavaConversions._

/**
  * spark-pagerank cn.upshi.sparkpagerank
  * 描述：
  * 时间：2017-4-1 14:28.
  */

@Component
class GraphxPageRank {

    def pageRank(taskId: Integer): java.util.List[PageRankResult] = {

        val conf = new SparkConf().setAppName("SinaPageRank")
            .setMaster("local")
            .set("spark.executor.memory", "2G");
        val sc = new SparkContext(conf);

        var fileName: String = FileUtil.linkFileName(taskId)

        // Load the edges as a graph
        val graph = GraphLoader.edgeListFile(sc, fileName)
        // Run PageRank
        val ranks = graph.pageRank(0.0001).vertices

        val sorted = ranks.sortBy(x => x._2, false).take(10)

        var list: java.util.List[PageRankResult] = new java.util.ArrayList()
        for(i <- 0 to sorted.length-1 ) {
            list.add(toPageRankResult(taskId, sorted(i)._1, sorted(i)._2))
        }
        list
    }

    def toPageRankResult(taskId: Integer, id: VertexId, rank: Double): PageRankResult = {
        new PageRankResult(taskId, id.toInt, rank)
    }

    def main(args: Array[String]): Unit = {

    }

}
