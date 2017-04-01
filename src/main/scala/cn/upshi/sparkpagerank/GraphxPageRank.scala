package cn.upshi.sparkpagerank

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.GraphLoader

/**
  * spark-pagerank cn.upshi.sparkpagerank
  * 描述：
  * 时间：2017-4-1 14:28.
  */

object GraphxPageRank {


    def main(args: Array[String]): Unit = {

        val conf = new SparkConf().setAppName("SinaPageRank")
            .setMaster("local")
            .set("spark.executor.memory", "2G");
        val sc = new SparkContext(conf);


        // Load the edges as a graph
        val graph = GraphLoader.edgeListFile(sc, "link.txt")
        // Run PageRank
        val ranks = graph.pageRank(0.0001).vertices

        val sorted = ranks.sortBy(x => x._2, false)

        sorted.saveAsTextFile("result")

    }

}
