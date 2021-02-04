ZAMFIRESCU RADU IOAN, 322CD

Despre implementare

    Am creat clase separat pentru fiecare entitate, dupa cum urmeaza:
     actor/Actor - contine informatiile din input pentru un actor
     entertainment/Video - contine informatii comune necesare serialelor si filmelor
     entertainment/Film - extinde Video si implementeaza metodele abstracte pentru
            durata si rating
     entertainment/Serial - similar cu Film
     repository/User - contine atributele unui user si istoricul activitaii sale



    Pentru platforma, exista clasa DataBase din pachetul repository, care contine
    multimi pentru fiecare entitate, ce sunt actualizate pe masura ce se executa
    actiuni. Pentru a modifica repo-ul si a rezolva cerintele temei, am creat
    clase pentru fiecare comanda astfel:

    In pachetul actions, exista 3 pachete in care sunt prezente clase pentru
    fiecare tip de actiune care implementeaza interfata Action:

    * commands - contine clasele ce realizeaza modificari asupra repository-ului
        existand, de asemenea, o clasa parinte Command care contine informatii
        necesare (nu pe toate) tuturor subclaselor

    * queries - include inca 3 pachete unde se regasesc clase pentru fiecare
        tip de query:
            ~ actors - include query-urile pentru actori
            ~ users - contine logica unui query pentru useri
            ~ videos - aici sunt clasele pentru fiecare interogare despre
                video-uri

    * recommendation - contine strategiile pentru fiecare tip de recomandare

    In Utils au fost adaugat metode statice a caror necesitate se intalneste
    de mai multe ori. De asemenea clasa Pair a fost inclusa in pachetul utils,
    deoarece a fost de folos de multe ori sa se stocheze perechi de date.

    In Constants s-au introdus date necesare la citirea/scrierea
    informatiilor/rezultatelor din/in fisiere.
