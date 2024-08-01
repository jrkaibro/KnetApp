package br.com.knetapp.service;

import jdk.nashorn.internal.runtime.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.Base64;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

public class ServiceMethod {

	String tempFile = "temp.pdf";

	@Logger
	public String intimprimir(String documento) {

		try {

			byte[] fileBytes = Base64.getDecoder().decode(documento);

			try (FileOutputStream fos = new FileOutputStream(tempFile)) {
				fos.write(fileBytes);
				fos.close();
			}

			PDDocument doc = PDDocument.load(new File(tempFile));

			// Definir os atributos do trabalho de impressão
			PrintRequestAttributeSet printAttributes = new HashPrintRequestAttributeSet();
			PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPageable(new PDFPageable(doc));
			job.setPrintService(printService);


			if (printService != null) {
				// Criar um trabalho de impressão
				DocPrintJob printJob = printService.createPrintJob();

				// Adicionar um PrintJobListener opcional para monitorar o status do trabalho de impressão
				printJob.addPrintJobListener(new PrintJobAdapter() {
					@Override
					public void printJobCompleted(PrintJobEvent pje) {
						System.out.println("Trabalho de impressão concluído.");
					}

					@Override
					public void printJobFailed(PrintJobEvent pje) {
						System.out.println("Trabalho de impressão falhou.");
					}
				});

				// Enviar o trabalho de impressão
				job.print();

				System.out.println("Trabalho de impressão enviado com sucesso.");
			} else {
				System.out.println("Nenhum serviço de impressão encontrado.");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PrinterException e) {
            throw new RuntimeException(e);
        }

        return "Success";
	}
}
