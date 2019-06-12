package io.apibuilder.validation.zip

import java.io.File

import io.apibuilder.validation.helpers
import org.scalatest.{FunSpec, Matchers}

class ZipFileSpec extends FunSpec with Matchers
  with helpers.FileHelpers
{
  it("withFile defaults to file name") {
    val tmp = File.createTempFile("foo", "json")
    val zip = ZipFileBuilder().withFile(tmp).build()
    ZipFileReader.fromFile(zip).entries.map(_.name) should equal(
      Seq(tmp.getName)
    )
  }

  it("withFile respects given name") {
    val tmp = File.createTempFile("foo", "json")
    val zip = ZipFileBuilder().withFile("bar.json", tmp).build()
    ZipFileReader.fromFile(zip).entries.map(_.name) should equal(
      Seq("bar.json")
    )
  }

  it("files are preserved") {
    val zip = ZipFileBuilder()
      .withFile("foo.json", writeToTempFile("foo"))
      .withFile("bar.json", writeToTempFile("bar"))
      .build()
    val entries = ZipFileReader.fromFile(zip).entries
    entries.map(_.name).sorted should equal(
      Seq("bar.json", "foo.json")
    )
    def read(name: String): String = {
      readFileAsString(entries.find(_.name == name).get.file)
    }
    read("bar.json") should equal("bar")
    read("foo.json") should equal("foo")
  }
}