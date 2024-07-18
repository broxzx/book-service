/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { findAllFeedbacksByBook } from '../fn/feedback-controller/find-all-feedbacks-by-book';
import { FindAllFeedbacksByBook$Params } from '../fn/feedback-controller/find-all-feedbacks-by-book';
import { PageResponseFeedbackResponse } from '../models/page-response-feedback-response';
import { save } from '../fn/feedback-controller/save';
import { Save$Params } from '../fn/feedback-controller/save';

@Injectable({ providedIn: 'root' })
export class FeedbackControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `save()` */
  static readonly SavePath = '/feedbacks';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `save()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  save$Response(params: Save$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return save(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `save$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  save(params: Save$Params, context?: HttpContext): Observable<number> {
    return this.save$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `findAllFeedbacksByBook()` */
  static readonly FindAllFeedbacksByBookPath = '/feedbacks/book/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllFeedbacksByBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllFeedbacksByBook$Response(params: FindAllFeedbacksByBook$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseFeedbackResponse>> {
    return findAllFeedbacksByBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllFeedbacksByBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllFeedbacksByBook(params: FindAllFeedbacksByBook$Params, context?: HttpContext): Observable<PageResponseFeedbackResponse> {
    return this.findAllFeedbacksByBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseFeedbackResponse>): PageResponseFeedbackResponse => r.body)
    );
  }

}
