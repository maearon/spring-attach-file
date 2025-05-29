import { Component,  OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { RouterModule } from "@angular/router"
import { NgxPaginationModule } from "ngx-pagination"
import { ToastrService } from "ngx-toastr"
import { Store } from "@ngrx/store"
import { Observable } from "rxjs"
import { User } from "../../models/user.model"
import { UserService } from "../../services/user.service"
import { selectUser } from "../../store/session/session.selectors"

@Component({
  selector: "app-users",
  standalone: true,
  imports: [CommonModule, RouterModule, NgxPaginationModule],
  template: `
    <div class="container py-4">
      <h1 class="mb-4">All users</h1>

      <div *ngIf="loading" class="text-center py-4">
        <div class="spinner-border" role="status">
          <span class="visually-hidden">Loading...</span>
        </div>
      </div>

      <div *ngIf="!loading">
        <div *ngIf="users.length === 0" class="text-center py-4">
          <p>No users found.</p>
        </div>

        <ul *ngIf="users.length > 0" class="list-group">
          <li *ngFor="let user of users | paginate: { itemsPerPage: 10, currentPage: page, totalItems: totalCount }"
              class="list-group-item d-flex align-items-center">
            <img
              [src]="user.gravatar"
              [alt]="user.name"
              class="rounded-circle me-3"
              width="50"
              height="50"
            >

            <a [routerLink]="['/users', user.id]" class="flex-grow-1">{{ user.username }}</a>

            <!-- <button
              *ngIf="currentUser$ | async as currentUser; else noUser"
              class="btn btn-sm btn-danger"
              (click)="confirmDelete(user.id, user.name)"
              [disabled]="currentUser.id === user.id"
            >
              delete
            </button> -->

            <ng-template #noUser></ng-template>
          </li>
        </ul>

        <div *ngIf="totalCount > 10" class="d-flex justify-content-center mt-4">
          <pagination-controls
            (pageChange)="handlePageChange($event)"
          ></pagination-controls>
        </div>
      </div>
    </div>
  `,
})
export class UsersComponent implements OnInit {
  users: User[] = []
  loading = true
  page = 1
  totalCount = 0
  currentUser$: Observable<User | null>

  constructor(
    private userService: UserService,
    private toastr: ToastrService,
    private store: Store,
  ) {
    this.currentUser$ = this.store.select(selectUser)
  }

  ngOnInit(): void {
    this.fetchUsers()
  }

  fetchUsers(): void {
    this.loading = true
    this.userService.getUsers({ page: this.page }).subscribe({
      next: (response) => {
        this.users = response.content
        this.totalCount = response.totalElements
        this.loading = false
      },
      error: (error) => {
        console.error(error)
        this.toastr.error("Failed to fetch users")
        this.loading = false
      },
    })
  }

  handlePageChange(newPage: number): void {
    this.page = newPage
    this.fetchUsers()
  }

  confirmDelete(userId: string, userName: string): void {
    if (confirm(`Are you sure you want to delete ${userName}?`)) {
      this.userService.deleteUser(userId).subscribe({
        next: (response) => {
          if (response.flash) {
            this.toastr.success(response.flash[1])
          } else {
            this.toastr.success("User deleted successfully")
          }
          this.fetchUsers()
        },
        error: (error) => {
          console.error(error)
          this.toastr.error("Failed to delete user")
        },
      })
    }
  }
}
