// import { Injectable } from '@angular/core';
// import html2pdf from 'html2pdf.js';

// @Injectable({
//   providedIn: 'root',
// })
// export class PdfExportService {
//   constructor() {}

//   public exportToPdf(elementId: string): void {
//     const element = document.getElementById(elementId);
//     if (element) {
//       const opt = {
//         margin: 0,
//         filename: 'document.pdf',
//         image: { type: 'jpeg', quality: 0.98 },
//         html2canvas: { scale: 2 },
//         jsPDF: { unit: 'in', format: 'a4', orientation: 'portrait' },
//       };
//       html2pdf().from(element).set(opt).save();
//     }
//   }
// }
