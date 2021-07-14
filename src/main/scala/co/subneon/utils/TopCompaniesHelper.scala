package co.subneon.utils

import co.subneon.domain.Company

import scala.collection.mutable.ListBuffer

object TopCompaniesHelper {

  // current top and min revenue
  type Accum = (ListBuffer[Company], Int)

  val EmptyAccum: Accum = (ListBuffer.empty[Company], 0)

  // It's looks strange but work better and faster then sort and copy immutable list
  // Besides, writing algorithms is fun ^^
  def process(topSize: Int)(accum: Accum, company: Company): Accum = {
    val (top, minRevenue) = accum

    if (top.size == topSize) {
      if (minRevenue > company.revenue) {
        accum
      } else {
        var insertIdx = 0
        var isBiggest = true
        for (i <- top.indices)
          if (top(i).revenue > company.revenue) {
            insertIdx = i
            isBiggest = false
          }

        if (isBiggest) {
          top.append(company)
        } else {
          top.insert(insertIdx, company)
        }

        top.dropInPlace(1)

        (top, top.head.revenue)
      }
    } else {
      val newMinRevenue = math.min(minRevenue, company.revenue)

      if (top.size == (topSize - 1)) {
        (top.append(company).sortBy(_.revenue), newMinRevenue)
      } else {
        (top.append(company), newMinRevenue)
      }
    }
  }

}
