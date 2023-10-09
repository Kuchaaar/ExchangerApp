# NBP Kursy walut
## -funkcjonalność pobierania dat świąt z zewnętrznego [HOLIDAYS API](https://date.nager.at/api/v3/PublicHolidays/), dane zaczytywane są przy starcie aplikacji jeśli w bazie danych nie ma odpowiednich wpisów ze świętami w danym roku
## -funkcjonalność pobierania walut z [NBP API](http://api.nbp.pl/api/exchangerates/tables/) w dni pracujące o 10pm.
## -Response jako plik z excela
## -Testy jednostkowe
## - /dane - Pozwala na otrzymanie danych z podanego okresu czasu
## - /currency - Pozwala na otrzymanie danych podanej waluty z podanego okresu czasu
## - /currency/extensions - Pozwala na otrzymanie danych podanej waluty z podanego okresu czasu
z dodatkowym liczeniem średnich
