package de.bit.workshop.moviedb.application;

import de.bit.workshop.moviedb.domain.api.Movie;
import de.bit.workshop.moviedb.domain.api.MovieDbService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/movies", produces = "application/json")
@AllArgsConstructor
public class MovieDbController {

    private MovieDbService movieDbService;

    @GetMapping
    public List<Movie> getAll() {
        return movieDbService.loadAllMovies();
    }

    @GetMapping("/{id}")
    public Movie get(@PathVariable String id) {
        return movieDbService.loadMovieById(UUID.fromString(id)).orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/{id}/cover")
    public String getCover(@PathVariable String id) {
        Movie movie = movieDbService.loadMovieById(UUID.fromString(id)).orElseThrow(ResourceNotFoundException::new);
        if (movie.getBase64Cover() == null) {
            throw new ResourceNotFoundException();
        }
        return movie.getBase64Cover();
    }

    @PostMapping
    public Movie createOrUpdate(@RequestBody Movie movie) {
        return movieDbService.createOrUpdateMovie(movie);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        movieDbService.deleteMovie(UUID.fromString(id));
    }
}
