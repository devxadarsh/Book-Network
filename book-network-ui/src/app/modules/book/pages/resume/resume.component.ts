import { PdfDownloadService } from './../../../../services/services/pdf-download.service';
import { Component } from '@angular/core';
import { PdfExportService } from '../../../../services/services/pdf-export.service';

@Component({
  selector: 'app-resume',
  standalone: true,
  imports: [],
  templateUrl: './resume.component.html',
  styleUrl: './resume.component.scss',
})
export class ResumeComponent {
  constructor(
    private pdfExportService: PdfExportService,
    public pdfDownloadService: PdfDownloadService
  ) {}

  public downloadPdf(): void {
    this.pdfExportService.exportToPdf('contentToExport');
  }
}
