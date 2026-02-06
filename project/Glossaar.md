# Glossaar

- **EN:** Glossary - a list ... consisting of pairs of entries, with a term and its definition making up each pair.
- **ET:** Glossaar - tundmatuid sõnu seletav sõnastik

---

## Ülevaade

**Eesmärk:** Isiklik sõnaraamat ja abiline uute teadmiste meeldejätmisel. Antud fail on jooksvalt täienev konseptsioon/roadmap lõpptulemini jõudmiseks.

---

## Peamised funktsionaalsused (P1)

1. Sõna ja definitsioon lisamine - kasutaja lisab uue sõna ja selle definitsiooni oma sõnaraamatusse.
2. Sõnade õppimine (quiz režiim) - kasutaja kinnitab oma õpitud teadmisi läbi Duolingo-laadse liidese. Kasutajale kuvatakse definitsioon ja juhuslikud alternatiivid, mille seast tuleb õige valida.
3. Kui kasutaja ise definitsiooni ei lisa, saab ta selle pärida välisest allikast.

## Vaated

- Sisselogimise ja registreerimise vaade - oauth2?
- Kirje lisamine/muutmise vaade
- Kirjete listivaade
- Quiz režiim vaade

## Lisanduvad funktsionaalsused

- kirjetele klassifikaatorite lisamine. (et quiz režiimis adekvaatseid alternatiive pakuks, võimaldab ka filtreerimise listivaates) - ise kasutaja lisab võib tuleb EKIst juba midagi kaasa?
- Inglise keele välise andmestiku liidestamine, nt [Wordnik](https://developer.wordnik.com/) või EKI toetab ka midagi?
- img kirje tüüp
- PWA
- i18n
- Mingisugune AI lahendus
- Priviligeeritud admin roll

---

## Platvorm

**Frontend:** Veebitehnoloogiad, mobile-first UI - Svelte

**Backend:** Java Spring REST API

**Andmebaas:** Postgres

---

## Andmestik

### Eesti keele andmebaas

Toetume [Eesti Keele Instituudi API](https://github.com/keeleinstituut/ekilex/wiki/Ekilex-API#overview)-le.

#### API võtme saamine

1. Mine https://ekilex.ee/
2. Logi sisse
3. Ava "Kasutaja Profiili haldus"
4. Genereeri personaalne Ekilex API võti

#### Päringute näited

**Sõna otsing:**

```bash
curl \
  --header "ekilex-api-key: secret-api-key" \
  https://ekilex.ee/api/meaning/search/koer
```

**Sõna detailid:**

```bash
curl \
  --header "ekilex-api-key: secret-api-key" \
  https://ekilex.ee/api/word/details/183007
```
