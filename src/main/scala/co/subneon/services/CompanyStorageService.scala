package co.subneon.services

import java.nio.file.Paths

import cats.effect.IO
import fs2.{Stream => Fs2Stream}
import fs2.data.csv
import fs2.io.file.{Files => Fs2Files}

import co.subneon.domain.Company
import co.subneon.protocol.csv.CompanyCsvCodec

trait CompanyStorageService {

  def read(): fs2.Stream[IO, Company]

  def write(data: Fs2Stream[IO, List[Company]]): Fs2Stream[IO, fs2.INothing]

}

object CompanyStorageService extends CompanyStorageService {
  import CompanyCsvCodec._

  private val dataPath   = Paths.get(getClass.getResource("/companies.csv").toURI)
  private val resultPath = Paths.get("top3_companies.csv")

  override def read(): fs2.Stream[IO, Company] =
    Fs2Files[IO]
      .readAll(dataPath, 1024)
      .through(fs2.text.utf8Decode)
      .through(csv.decodeUsingHeaders[Company]())

  override def write(data: Fs2Stream[IO, List[Company]]): Fs2Stream[IO, fs2.INothing] =
    data
      .flatMap(top => Fs2Stream.fromIterator[IO].apply(top.iterator, 3))
      .through(csv.encodeUsingFirstHeaders[Company]())
      .through(fs2.text.utf8Encode)
      .through(fs2.io.file.Files[IO].writeAll(resultPath))

}
