import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss',
})
export class MenuComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {
    const linkColor = document.querySelectorAll('.nav-link');
    linkColor.forEach((link) => {
      if (window.location.href.endsWith(link.getAttribute('href') || '')) {
        link.classList.add('active');
      }
      link.addEventListener('click', () => {
        linkColor.forEach((l) => l.classList.remove('active'));
        link.classList.add('active');
      });
    });
  }

  logout() {
    localStorage.removeItem('token');
    window.location.reload();
  }

  // ngOnInit(): void {
  //   const linkColor = document.querySelectorAll('.nav-link');

  //   // Function to update the active link based on the current route
  //   const updateActiveLink = () => {
  //     const currentRoute = this.router.url;
  //     linkColor.forEach((link) => {
  //       const linkRoute = link.getAttribute('routerLink');
  //       if (linkRoute && currentRoute.includes(linkRoute)) {
  //         link.classList.add('active');
  //       } else {
  //         link.classList.remove('active');
  //       }
  //     });
  //   };

  //   // Initially update the active link on page load
  //   updateActiveLink();

  //   // Update the active link whenever the route changes
  //   this.router.events
  //     .pipe(filter((event) => event instanceof NavigationEnd))
  //     .subscribe(() => {
  //       updateActiveLink();
  //     });
  // }
}
