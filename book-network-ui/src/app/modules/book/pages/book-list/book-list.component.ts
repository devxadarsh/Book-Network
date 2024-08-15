import { Component, OnInit } from '@angular/core';
import { BookService } from '../../../../services/services';
import { Router } from '@angular/router';
import { PageResponseBookResponse } from '../../../../services/models';
import { CommonModule } from '@angular/common';
import { BookCardComponent } from "../../components/book-card/book-card.component";

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [CommonModule, BookCardComponent],
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.scss'
})
export class BookListComponent implements OnInit{
  bookResponse: PageResponseBookResponse = {};
  page: number = 0;
  size: number = 5;
  title: string = '';

  constructor(
    private bookService: BookService,
    private router: Router 
  ) {
  }
  ngOnInit(): void {
    this.bookService.findAllBooks({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (books) => {
        this.bookResponse = books;
      }
    })
  }

}
