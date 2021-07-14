package co.subneon.protocol.json

import co.subneon.domain.CompanyFilter
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class FiltersDecoderSpec extends AnyWordSpec with Matchers {

  "FiltersDecoder" must {

    "decode right filters data" in {
      val result = List(
        CompanyFilter.NameContains("foo"),
        CompanyFilter.IsProfitable
      )
      val source =
        """
          |[
          |  {
          |    "filterType": "NameContains",
          |    "text": "foo"
          |  },
          |  {
          |    "filterType": "IsProfitable"
          |  }
          |]""".stripMargin

      FiltersDecoder.decode(source) shouldEqual Right(result)
    }

  }

}
