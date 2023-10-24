import {Component, OnInit} from '@angular/core';
import {ApiService} from '../shared/api.service';
import {FeedbackViewModel} from './model/feedbackviewmodel';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.css']
})
export class FeedbackComponent implements OnInit {

  model: FeedbackViewModel = {
    name: '',
    email: '',
    feedback: ''
  };

  constructor(private apiService: ApiService) {
  }

  ngOnInit(): void {
  }

  sendFeedback(): void {
    this.apiService.postFeedback(this.model).subscribe(
      res => {
        location.reload();
      },
      err => {
        alert('An error has occurred while sending feedback');
      }
    );
  }

}
