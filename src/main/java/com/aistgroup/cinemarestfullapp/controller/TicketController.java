package com.aistgroup.cinemarestfullapp.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.aistgroup.cinemarestfullapp.dto.request.SeatRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.TicketResponseModel;
import com.aistgroup.cinemarestfullapp.service.TicketService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tickets")
public class TicketController {

  private final TicketService ticketService;

  @PostMapping
  ResponseEntity<Void> purchaseTicket(@RequestBody List<SeatRequestModel> seatRequests,
      @RequestParam Long sessionId,
      @RequestParam Long userId) {
    ticketService.purchaseTicket(seatRequests, sessionId, userId);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  ResponseEntity<Void> returnTicket(@PathVariable Long id) {
    ticketService.returnTicket(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("users/{id}")
  ResponseEntity<CollectionModel<TicketResponseModel>> getTicketsByUser(@PathVariable Long id) {
    List<TicketResponseModel> tickets = ticketService.getTicketsByUser(id);

    tickets.forEach(ticket -> ticket.add(
        linkTo(methodOn(TicketController.class).getTicketById(ticket.getId())).withSelfRel()));

    CollectionModel<TicketResponseModel> responseModel = CollectionModel.of(tickets);
    responseModel.add(
        linkTo(methodOn(TicketController.class).getTicketsByUser(id)).withSelfRel());
    return ResponseEntity.ok(responseModel);
  }

  @GetMapping("/{id}")
  ResponseEntity<TicketResponseModel> getTicketById(@PathVariable Long id) {
    TicketResponseModel ticket = ticketService.getTicketById(id);

    ticket.add(linkTo(methodOn(TicketController.class).getTicketById(id)).withSelfRel());

    return ResponseEntity.ok(ticket);
  }


}
