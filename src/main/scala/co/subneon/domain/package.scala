package co.subneon

import fs2.data.csv.generic.CsvName

package object domain {

  final case class Company(
    @CsvName("CompanyName") name: String,
    @CsvName("Revenue") revenue: Int,
    @CsvName("IsProfitable") isProfitable: Boolean
  )

  trait CompanyFilter {
    def isPassed(company: Company): Boolean

    def compose(other: CompanyFilter): CompanyFilter = (company: Company) =>
      this.isPassed(company) && other.isPassed(company)
  }

  object CompanyFilter {

    case class NameContains(text: String) extends CompanyFilter {
      override def isPassed(company: Company): Boolean = company.name.contains(text)
    }

    case object IsProfitable extends CompanyFilter {
      override def isPassed(company: Company): Boolean = company.isProfitable
    }

  }

}
