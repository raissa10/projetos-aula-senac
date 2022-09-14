/*
 * Copyright (C) 2014 Gelvazio Camargo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Testes;

/**
 *
 * @author Gelvazio Camargo
 *
 * import it.businesslogic.ireport.connection.JRXMLDataSource;
 *
 * import java.util.HashMap;
 *
 * import net.sf.jasperreports.engine.JRException; import
 * net.sf.jasperreports.engine.JRExporter; import
 * net.sf.jasperreports.engine.JRExporterParameter; import
 * net.sf.jasperreports.engine.JasperFillManager; import
 * net.sf.jasperreports.engine.JasperPrint;
 *
 *
 * public class XMLDataSourceExample {
 *
 * public static void main(String[] args) throws Exception { String
 * reportFileName = "teste1.jasper"; String outFileName = "teste1.pdf"; String
 * xmlFileName = "1.xml"; String recordPath = "/ProgramaListagem/Programa";
 * JRXMLDataSource jrxmlds = new JRXMLDataSource(xmlFileName,recordPath);
 * HashMap hm = new HashMap(); try{ // JasperPrint print =
 * JasperFillManager.fillReport(reportFileName, hm, jrxmlds); JasperPrint print
 * = JasperFillManager.fillReport(reportFileName, null, jrxmlds); JRExporter
 * exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
 * exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,outFileName);
 * exporter.setParameter(JRExporterParameter.JASPER_PRINT,print);
 * exporter.exportReport(); System.out.println("Created file: " + outFileName);
 * } catch (JRException e) { e.printStackTrace(); System.exit(1); } catch
 * (Exception e) { e.printStackTrace(); System.exit(1); }
 *
 * }
 *
 * }
 *
 */
