package co.subneon.services

import cats.effect.IO
import co.subneon.domain.CompanyFilter
import co.subneon.utils.TopCompaniesHelper

trait CompanyService {

  def filterAndSaveTop(filters: List[CompanyFilter]): IO[Unit]

}

class CompanyServiceImpl(companyStorageService: CompanyStorageService) extends CompanyService {

  override def filterAndSaveTop(filters: List[CompanyFilter]): IO[Unit] = {
    val composedFilter = filters.reduce((f, s) => f.compose(s))

    companyStorageService
      .read()
      .filter(composedFilter.isPassed)
      .fold(TopCompaniesHelper.EmptyAccum)(TopCompaniesHelper.process(3))
      .map(_._1.sortBy(_.revenue)(Ordering.Int.reverse).toList)
      .through(companyStorageService.write)
      .compile
      .drain
  }

}
