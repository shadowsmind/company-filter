package co.subneon.protocol

import co.subneon.domain.Company

package object csv {

  type Fields = List[String]

  object CompanyCsvCodec {
    import fs2.data.csv._
    import fs2.data.csv.generic.semiauto._

    implicit val decoder: CsvRowDecoder[Company, String] = deriveCsvRowDecoder[Company]
    implicit val encoder: CsvRowEncoder[Company, String] = deriveCsvRowEncoder[Company]
  }

}
