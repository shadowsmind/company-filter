package co.subneon.protocol

import io.circe.{Decoder, HCursor}
import io.circe.Decoder.Result
import io.circe.DecodingFailure
import io.circe.parser
import io.circe

import co.subneon.domain.CompanyFilter

package object json {

  object FiltersDecoder {

    implicit val decoder: Decoder[CompanyFilter] = (c: HCursor) => {
      def extractFilterAndOptions(filterType: String): Result[CompanyFilter] = filterType match {
        case "NameContains" => c.downField("text").as[String].map(t => CompanyFilter.NameContains(t))
        case "IsProfitable" => Right(CompanyFilter.IsProfitable)
        case _              => Left(DecodingFailure("unknown filterType", c.history))
      }

      for {
        filterType <- c.downField("filterType").as[String]
        filter     <- extractFilterAndOptions(filterType)
      } yield filter
    }

    def decode(source: String): Either[circe.Error, List[CompanyFilter]] =
      parser.decode[List[CompanyFilter]](source)

  }

}
