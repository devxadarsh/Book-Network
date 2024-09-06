import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  BorrowedBookResponse,
  PageResponseBorrowedBookResponse,
} from '../../../../services/models';
import { BookService } from '../../../../services/services';

@Component({
  selector: 'app-borrowed-book-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './borrowed-book-list.component.html',
  styleUrl: './borrowed-book-list.component.scss',
})
export class BorrowedBookListComponent implements OnInit {
  borrowedBooks: PageResponseBorrowedBookResponse = {};
  page: number = 0;
  size: number = 1;

  constructor(private bookService: BookService) {}
  ngOnInit(): void {
    this.findAllBorrowedBooks();
  }
  private findAllBorrowedBooks() {
    this.bookService
      .findAllBorrowedBooks({
        page: this.page,
        size: this.size,
      })
      .subscribe({
        next: (resp) => {
          this.borrowedBooks = resp;
        },
      });
  }
  returnBorrowedBook(_t16: BorrowedBookResponse) {
    throw new Error('Method not implemented.');
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllBorrowedBooks();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllBorrowedBooks();
  }

  goToPage(page: number) {
    this.page = page;
    this.findAllBorrowedBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllBorrowedBooks();
  }

  goToLastPage() {
    this.page = (this.borrowedBooks.totalPage as number) - 1;
    this.findAllBorrowedBooks();
  }

  get isLastPage(): boolean {
    return this.page == (this.borrowedBooks.totalPage as number) - 1;
  }
}
