package co.subneon

import cats.effect.{IO, IOApp}
import co.subneon.services.{CompanyServiceImpl, CompanyStorageService, FilterStorageService}

object Boot extends IOApp.Simple {

  val companyService = new CompanyServiceImpl(CompanyStorageService)

  override def run: IO[Unit] =
    for {
      filters <- FilterStorageService.getAll
      result  <- companyService.filterAndSaveTop(filters)
    } yield result

}
