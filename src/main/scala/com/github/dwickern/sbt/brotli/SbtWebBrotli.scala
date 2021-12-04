package com.github.dwickern.sbt.brotli

import com.nixxcode.jvmbrotli.common.BrotliLoader
import com.nixxcode.jvmbrotli.enc.BrotliOutputStream
import com.typesafe.sbt.web.SbtWeb
import com.typesafe.sbt.web.pipeline.Pipeline
import sbt.Keys._
import sbt._

import java.io.FileOutputStream

object Import {
  val brotli = TaskKey[Pipeline.Stage]("brotli-compress", "Add brotli-compressed files to asset pipeline.")
}

object SbtWebBrotli extends AutoPlugin {
  override def requires: Plugins = SbtWeb
  override def trigger: PluginTrigger = allRequirements

  val autoImport = Import

  import SbtWeb.autoImport.WebKeys._
  import autoImport._

  override def projectSettings: Seq[Setting[_]] = Seq(
    brotli / includeFilter := "*.html" || "*.css" || "*.js",
    brotli / excludeFilter := HiddenFileFilter || "*.woff" || "*.woff2" || "*.gz",
    brotli / target := webTarget.value / brotli.key.label,
    deduplicators += SbtWeb.selectFileFrom((brotli / target).value),
    brotli := brotliFiles.value
  )

  private def brotliFiles: Def.Initialize[Task[Pipeline.Stage]] = Def.task {
    if (!BrotliLoader.isBrotliAvailable) {
      sys.error("Brotli native library could not be loaded")
    }
    val targetDir = (brotli / target).value
    val include = (brotli / includeFilter).value
    val exclude = (brotli / excludeFilter).value
    mappings =>
      val brotliMappings = for {
        (file, path) <- mappings
        if !file.isDirectory && include.accept(file) && !exclude.accept(file)
      } yield {
        val brotliPath = path + ".br"
        val brotliFile = targetDir / brotliPath
        brotliFile.getParentFile.mkdirs()

        // brotli compress
        val out = new BrotliOutputStream(new FileOutputStream(brotliFile))
        try IO.transfer(file, out) finally out.close()

        brotliFile -> brotliPath
      }
      mappings ++ brotliMappings
  }
}
