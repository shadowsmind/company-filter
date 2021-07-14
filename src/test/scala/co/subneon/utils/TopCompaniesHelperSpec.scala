package co.subneon.utils

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import co.subneon.domain.Company

class TopCompaniesHelperSpec extends AnyWordSpec with Matchers {

  "TopCompaniesHelper" must {

    "right fold list companies to top" in {
      val companies = List(
        Company("Company foo 1", 101, true),
        Company("Company foo 3", 103, true),
        Company("Company foo 4", 104, true),
        Company("Company foo 5", 105, true)
      )

      val rightTop3 = List(
        Company("Company foo 5", 105, true),
        Company("Company foo 4", 104, true),
        Company("Company foo 3", 103, true)
      )

      val result = companies
        .foldLeft(TopCompaniesHelper.EmptyAccum)(TopCompaniesHelper.process(3))
        ._1
        .toList
        .sortBy(_.revenue)(Ordering.Int.reverse)

      result shouldEqual rightTop3
    }

  }

}
