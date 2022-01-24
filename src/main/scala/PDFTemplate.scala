package mohr.jonas.pdf.splitter

import org.apache.pdfbox.pdmodel.{PDDocument, PDPage}

import java.nio.file.Path
import java.util
import java.util.ArrayList

class PDFTemplate(val path: Path) {

  private val pages = new util.ArrayList[PDPage]()

  def +=(page: PDPage): Unit = {
    pages.add(page)
  }

  def removeLastPage(): Unit = {
    if (pages.size() == 0) return
      pages.remove(pages.size() - 1)
  }

  def savePDF(): Unit = {
    val document = new PDDocument()
    pages.forEach(document.addPage(_))
    document.save(path.toFile)
  }
}
