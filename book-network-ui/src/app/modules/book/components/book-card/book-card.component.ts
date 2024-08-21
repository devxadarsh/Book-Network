import { Component, input, Input } from '@angular/core';
import { BookResponse } from '../../../../services/models';
import { CommonModule } from '@angular/common';
import { RatingComponent } from '../rating/rating.component';

@Component({
  selector: 'app-book-card',
  standalone: true,
  imports: [CommonModule, RatingComponent],
  templateUrl: './book-card.component.html',
  styleUrl: './book-card.component.scss',
})
export class BookCardComponent {
  private _book: BookResponse = {};

  get book(): BookResponse {
    return this._book;
  }

  @Input()
  set book(value: BookResponse) {
    this._book = value;
  }

  private _bookCover: string | undefined;

  get bookCover(): string | undefined {
    if (this.book.cover) {
      return 'data:image/jpg;base64, ' + this._book.cover;
    }
    // return this._bookCover;
    return 'https://i0.wp.com/christianlydemann.com/wp-content/uploads/2024/05/Angular-Mastery.png?w=1410&ssl=1';
  }

  private _manage: boolean = false;

  get manage(): boolean {
    return this._manage;
  }

  @Input()
  set manage(value: boolean) {
    this._manage = value;
  }
}
