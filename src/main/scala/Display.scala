package mohr.jonas.pdf.splitter

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer

import java.awt.Graphics
import java.awt.image.BufferedImage
import javax.swing.JFrame

class Display(doc: PDDocument) extends JFrame("Display") {
  setSize(600, 800)
  setLocationRelativeTo(null)
  setUndecorated(true)
  setDefaultCloseOperation(3)
  setVisible(true)

  private val renderer = new PDFRenderer(doc)
  private var buffer: Option[BufferedImage] = None

  def displayPage(pageNumber: Int): Unit = {
    buffer = Some(renderer.renderImage(pageNumber))
    repaint()
  }

  override def paint(g: Graphics): Unit = {
    g.clearRect(0, 0, 600, 800)
    if (buffer.nonEmpty)
      g.drawImage(buffer.get, 0, 0, 600, 800, null)
  }
}
