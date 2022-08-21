import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Rental } from 'src/app/features/rentals/interfaces/rental.interface';
import { SessionService } from 'src/app/services/session.service';
import { MessageRequest } from '../../interfaces/messageRequest.interface';
import { MessageResponse } from '../../interfaces/messageResponse.interface';
import { MessagesService } from '../../services/messages.service';
import { RentalsService } from '../../services/rentals.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  public messageForm!: FormGroup;
  public rental: Rental | undefined;

  constructor(private fb: FormBuilder,
    private messagesService: MessagesService,
    private rentalsService: RentalsService,
    private sessionService: SessionService,
    private matSnackBar: MatSnackBar) {
    this.initMessageForm();
  }

  public ngOnInit(): void {
    this.rentalsService
      .detail('2')
      .subscribe((rental: Rental) => this.rental = rental);
  }

  public back() {
    window.history.back();
  }

  public sendMessage(): void {
    const message = {
      owner_id: this.rental!.owner_id,
      user_id: this.sessionService.user?.id,
      message: this.messageForm.value.message
    } as MessageRequest;

    this.messagesService.send(message).subscribe(
      (messageResponse: MessageResponse) => {
        this.initMessageForm();
        this.matSnackBar.open(messageResponse.message, "Close", { duration: 3000 });
      });
  }

  private initMessageForm() {
    this.messageForm = this.fb.group({
      message: ['', [Validators.required, Validators.min(10)]],
    });
  }

}
