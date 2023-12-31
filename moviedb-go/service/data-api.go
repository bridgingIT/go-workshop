package service

import (
	"github.com/go-workshop/moviedb/model"
)

type MovieRepository interface {
	FindAll() (*[]model.Movie, error)
	FindById(id string) (*model.Movie, error)
	CreateOrUpdate(movie *model.Movie) (*model.Movie, error)
	Delete(id string)
}
