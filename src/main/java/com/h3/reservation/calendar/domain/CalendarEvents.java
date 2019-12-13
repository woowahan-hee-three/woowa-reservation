package com.h3.reservation.calendar.domain;

import com.google.api.services.calendar.model.Event;
import com.h3.reservation.calendar.utils.SummaryParser;
import com.h3.reservation.common.MeetingRoom;

import java.util.List;
import java.util.stream.Collectors;

public class CalendarEvents {

    private static final int INDEX_OF_MEETING_ROOM = 0;

    private final List<Event> events;

    public CalendarEvents(List<Event> events) {
        this.events = events;
    }

    public List<String> findSummaries() {
        return events.stream()
            .map(Event::getSummary)
            .collect(Collectors.toList());
    }

    public List<MeetingRoom> findMeetingRooms(final String summaryDelimiter) {
        return findSummaries().stream()
            .map(summary -> SummaryParser.parse(summary, summaryDelimiter))
            .map(tokens -> tokens.get(INDEX_OF_MEETING_ROOM))
            .map(roomName -> roomName.replace(" ", ""))
            .map(MeetingRoom::findByName)
            .collect(Collectors.toList());
    }

    public int size() {
        return events.size();
    }

    public Event getEvent(int index) {
        return events.get(index);
    }

    public List<Event> getEvents() {
        return events;
    }
}