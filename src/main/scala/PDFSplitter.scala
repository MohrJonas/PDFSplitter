package mohr.jonas.pdf.splitter

import org.apache.pdfbox.pdmodel.{PDDocument, PDPage}
import org.apache.pdfbox.rendering.PDFRenderer

import java.io.File
import java.nio.file.Path
import java.util
import java.util.ArrayList
import javax.swing.JOptionPane

object PDFSplitter {
  def main(args: Array[String]): Unit = {
    val pdf = PDDocument.load(new File(args(0)))
    val outputFolder = Path.of(args(1))
    println(s"Loaded ${pdf.getNumberOfPages} pages")
    val display = new Display(pdf)
    val templates = new util.ArrayList[PDFTemplate]()
    for (i <- Range(0, pdf.getNumberOfPages)) {
      display.displayPage(i)
      askIsNewDocument() match {
        case JOptionPane.YES_OPTION => templates.add(new PDFTemplate(outputFolder.resolve(s"$i.pdf")))
        case JOptionPane.NO_OPTION => templates.get(templates.size() - 1) += pdf.getPage(i)
        case JOptionPane.CANCEL_OPTION => askDiscardChanges() match {
          case JOptionPane.YES_OPTION => System.exit(0)
          case JOptionPane.NO_OPTION =>
            savePDFs(templates)
            System.exit(0)
          case JOptionPane.CANCEL_OPTION =>
        }
      }
    }
    savePDFs(templates)
  }

  private def askIsNewDocument(): Int = {
    JOptionPane.showConfirmDialog(null, "Is this the beginning of a new doc?")
  }

  private def askDiscardChanges(): Int = {
    JOptionPane.showConfirmDialog(null, "Do you want to discard the changes?")
  }

  private def savePDFs(templates: util.ArrayList[PDFTemplate]): Unit = {
    templates.forEach(_.savePDF())
  }
}
