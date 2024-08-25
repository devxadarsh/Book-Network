import { Event } from '@angular/router';
// import { FindAllBooks } from './../../../../services/fn/book/find-all-books';
import { Component, OnInit } from '@angular/core';
import { BookService } from '../../../../services/services';
import { Router } from '@angular/router';
import {
  BookResponse,
  PageResponseBookResponse,
} from '../../../../services/models';
import { CommonModule } from '@angular/common';
import { BookCardComponent } from '../../components/book-card/book-card.component';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [CommonModule, BookCardComponent],
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.scss',
})
export class BookListComponent implements OnInit {
  // islastPage: any;

  bookResponse: PageResponseBookResponse = {};
  page: number = 0;
  size: number = 3;
  title: string = '';
  message: string = '';
  level: string = 'success';

  constructor(private bookService: BookService, private router: Router) {}

  ngOnInit(): void {
    this.findAllBooks();
  }

  getVisiblePages(): number[] {
    const totalPages = this.bookResponse.totalPage ?? 0;
    const currentPage = this.page;

    const startPage = Math.max(currentPage - 1, 0);
    const endPage = Math.min(currentPage + 1, totalPages - 1);

    const pages: number[] = [];
    for (let i = startPage; i <= endPage; i++) {
      pages.push(i);
    }

    return pages;
  }

  findAllBooks() {
    this.bookService
      .findAllBooks({
        page: this.page,
        size: this.size,
      })
      .subscribe({
        next: (books) => {
          this.bookResponse = books;
          console.log(books);
        },
      });
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllBooks();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllBooks();
  }

  goToPage(page: number) {
    this.page = page;
    this.findAllBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllBooks();
  }

  goToLastPage() {
    this.page = (this.bookResponse.totalPage as number) - 1;
    this.findAllBooks();
  }

  get isLastPage(): boolean {
    return this.page == (this.bookResponse.totalPage as number) - 1;
  }

  borrowbook(book: BookResponse) {
    console.log(book);
    this.message = '';
    this.bookService
      .borrowBook({
        'book-id': book.id as number,
      })
      .subscribe({
        next: () => {
          this.level = 'success';
          this.message = 'Book successfully added to your list';
        },
        error: (err) => {
          console.log(err);
          this.level = 'error';
          this.message = err.error.error;
        },
      });
  }

  // borrowbook(event: any) {
  //   const book = event as BookResponse; // Cast the event to BookResponse
  //   this.message = '';
  //   this.bookService
  //     .borrowBook({
  //       'book-id': book.id as number,
  //     })
  //     .subscribe({
  //       next: () => {
  //         this.level = 'success';
  //         this.message = 'Book successfully added to your list';
  //       },
  //       error: (err) => {
  //         console.log(err);
  //         this.level = 'error';
  //         this.message = err.error.error;
  //       },
  //     });
  // }
}
