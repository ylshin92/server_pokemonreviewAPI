package com.pokemonreview.api.controllers;

import com.pokemonreview.api.dto.PageResponse;
import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.models.PokemonType;
import com.pokemonreview.api.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<?>> getPokemons(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize) {
        return new ResponseEntity<>(pokemonService.getAllPokemon(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PokemonDto> pokemonDetail(@PathVariable int id) {
        return ResponseEntity.ok(pokemonService.getPokemonById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto) {
        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PokemonDto> updatePokemon(@RequestBody PokemonDto pokemonDto,
                                                    @PathVariable("id") int pokemonId) {
        PokemonDto response = pokemonService.updatePokemon(pokemonDto, pokemonId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int pokemonId) {
        pokemonService.deletePokemonId(pokemonId);
        return new ResponseEntity<>("Pokemon delete", HttpStatus.OK);
    }

    @GetMapping("/pokemontypes")
    public ResponseEntity<List<String>> getPokemonTypes() {
        return new ResponseEntity<>(PokemonType.nameList(), HttpStatus.OK);
    }
}