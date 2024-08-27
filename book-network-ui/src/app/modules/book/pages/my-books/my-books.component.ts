import { Component } from '@angular/core';
import { PageResponseBookResponse } from '../../../../services/models/page-response-book-response';
import { BookService } from '../../../../services/services/book.service';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { BookCardComponent } from '../../components/book-card/book-card.component';
import { BookResponse } from '../../../../services/models';

@Component({
  selector: 'app-my-books',
  standalone: true,
  imports: [CommonModule, BookCardComponent, RouterLink],
  templateUrl: './my-books.component.html',
  styleUrl: './my-books.component.scss',
})
export class MyBooksComponent {
  // islastPage: any;

  bookResponse: PageResponseBookResponse = {};
  page: number = 0;
  size: number = 2;
  title: string = '';

  constructor(private bookService: BookService, private router: Router) {}

  ngOnInit(): void {
    this.findAllBooks();
  }

  findAllBooks() {
    this.bookService
      .findAllBooksByOwner({
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
    this.findAllBooks;
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

  archiveBook(book: BookResponse) {
    this.bookService
      .updateArchivedStatus({
        'book-id': book.id as number,
      })
      .subscribe({
        next: () => {
          book.archived = !book.archived;
        },
      });
  }

  shareBook(book: BookResponse) {
    this.bookService
      .updateSharableStatus({
        'book-id': book.id as number,
      })
      .subscribe({
        next: () => {
          book.shareable = !book.shareable;
        },
      });
  }

  editBook(book: BookResponse) {
    this.router.navigate(['books', 'manage', book.id]);
  }
}
