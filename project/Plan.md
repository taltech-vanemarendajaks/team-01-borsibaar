# Plan

Plaani puhul prioritiseerime vastavalt Glossaar.md roadmap failis kirjeldatud peamistele funktsionaalsustele.

## Setup

- [ ] Loo uus repo + ligipääsud + protected main
- [ ] Svelte starter project
- [ ] Java Spring starter project
- [ ] ise nullist authi ei teeks - oauth2 integratsioon BE (kas FE tahab ka midagi?)
- [ ] mingisugune ORM lahendus üles seada (Java maailma standard on JPA?)
- [ ] mingisugune light-weight svelte komponentide raamistik üles seada (btn, card, textfield, mida veel vaja?) bootstrap / tailwind / miski muu?
- [ ] võiks mingi automaatne format-on-save lahendus olla BE
- [ ] võiks mingi automaatne format-on-save lahendus olla FE (eslint + prettier? miskit tuleb ehk automaatselt kaasa)
- [ ] kuidas lokaalselt jooksutame? (ka docker?)

## developemnt

- [ ] mingi algne andmebaasi word tabelite koosseis (user >-< user_word >-< word_key >-< word_value(saab custom word puhul omaniku külge panna))
- [ ] User / auth endpointid
- [ ] BE põhilised word endpointid - toimetada saab vaid enda ressurssidega:
  - [ ] POST words/{id}
  - [ ] PATCH words/{id}
  - [ ] DELETE words/{id} -  kustutab seose kasutaja ja sõna vahel. v.a. kui on custom word, siis kustutame sõna.
  - [ ] GET words?filter=abs + pagination?
- [ ] FE vaated:
  - [ ] Kirje lisamise/muutmise vaade
  - [ ] Kirjete listivaade + filtrid + kustutamine + paginatsioon
  - [ ] Quiz režiim vaade
- [ ] põhilised quiz endpointid (algselt võib olla loogika ainult FE poolel?)
  - [ ] mingi algne andmebaasi quiz tabelite koosseis (user_quiz - kasutajal oli nö seanss. user_quiz_result - seansside tulemused)
  - [ ] POST quiz (seesmiselt tehakse words?filter=quiz&count=4 ja lisatakse, et milline on õige + luuakse user_quiz kirje ja sisestatakse user_quiz_result null väärtustega)
  - [ ] POST quiz-results - APIs muudetakse vastavalt scorei, (FE saadab { userQuizId + mida valis õigesti, mida valesti })
  - [ ] GET words quiz filter arvesse võtma quiz-results tulemusi (küsida rohkem neid, millel skoor madal, aga alles siis kui küsimata sõnu ei ole).

## CI/CD

- [ ] 1. Serverise docker
- [ ] 2. Serverisse nginx
- [ ] 3. Serverisse andmebaas püsti panna (saab sama psql instance kasutada mis borsibaar)
- [ ] FE build + ghrc deploy action + secretid (server + vajadusel GH)
- [ ] BE build + ghrc BE deploy action + secretid (server + vajadusel GH)
- [ ] Testide jooksutamine PRi avades, edukas run saab olema eeldus mergemiseks

## Design

- [ ] vaadete wireframe'id luua vastavalt roadmapis väljatoodule
