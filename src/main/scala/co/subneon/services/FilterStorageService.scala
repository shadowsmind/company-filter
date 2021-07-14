package co.subneon.services

import scala.io.Source
import cats.effect.{IO, Resource}

import co.subneon.domain.CompanyFilter
import co.subneon.protocol.json.FiltersDecoder

trait FilterStorageService {

  def getAll: IO[List[CompanyFilter]]

}

object FilterStorageService extends FilterStorageService {

  // there are not many filters, so we can load them into memory all at once
  override def getAll: IO[List[CompanyFilter]] =
    Resource
      .fromAutoCloseable(IO(Source.fromResource("filters.json")))
      .use(file => IO(file.getLines().mkString))
      .flatMap(jsonString => IO.fromEither(FiltersDecoder.decode(jsonString)))

}
