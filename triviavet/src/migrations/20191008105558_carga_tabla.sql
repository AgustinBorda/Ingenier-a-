INSERT INTO categories(nombre) VALUES ("Examen clínico"),
                                      ("Farmacología y Terapéutica"),
                                      ("Enfermedades Infecciosas y Parasitarias"),
                                      ("Clínica Médica"),
                                      ("Clínica Quirúrgica"),
                                      ("Manejo Poblacional");

INSERT INTO questions (id, category, description, wrong_attempts, right_attempts, total_attempts) VALUES
	  (1, "Enfermedades Infecciosas y Parasitarias", "Los signos clínicos de ostertagiasis pueden ser:" ,0,0,0),
	  (2, "Enfermedades Infecciosas y Parasitarias", "Haemonchus spp ejerce su acción patógena en:" ,0,0,0),
	  (3, "Enfermedades Infecciosas y Parasitarias", "La ostertagiasis se trata con:" ,0,0,0),
	  (4, "Enfermedades Infecciosas y Parasitarias", "Los signos clínicos de fascioliasis pueden ser:" ,0,0,0),
	  (5, "Enfermedades Infecciosas y Parasitarias", "La fascioliasis se trata con:" ,0,0,0),
	  (6, "Enfermedades Infecciosas y Parasitarias", "Diarrea es signo clínico característico de:" ,0,0,0),
	  (7, "Enfermedades Infecciosas y Parasitarias", "Anemia y edema son característicos de:" ,0,0,0),
	  (8, "Enfermedades Infecciosas y Parasitarias", "Es signo característico de dictiocaulosis:" ,0,0,0),
	  (9, "Enfermedades Infecciosas y Parasitarias", "La coccidiosis se trata con:" ,0,0,0),
	  (10, "Enfermedades Infecciosas y Parasitarias", "Albendazol es eficaz para tratar:" ,0,0,0),
	  (11, "Farmacología y Terapéutica", "La posología de Enrofloxacina es:" ,0,0,0),
	  (12, "Farmacología y Terapéutica", "La posología de Danofloxacina es:" ,0,0,0),
	  (13, "Farmacología y Terapéutica", "La posología de Florfenicol es:" ,0,0,0),
	  (14, "Farmacología y Terapéutica", "La posología de Oxitetraciclina L.A. es:" ,0,0,0),
	  (15, "Farmacología y Terapéutica", "La posología de Clorhidrato de Oxitetraciclina es:" ,0,0,0),
	  (16, "Farmacología y Terapéutica", "La posología de Amoxicilina es:" ,0,0,0),
	  (17, "Farmacología y Terapéutica", "La dosis y vía de Adm. de Tilmicosina para Ap. Respiratorio es:" ,0,0,0),
	  (18, "Farmacología y Terapéutica", "La dosis y vía de Adm. de Tilmicosina para Ojo o Pie es:" ,0,0,0),
	  (19, "Farmacología y Terapéutica", "La posología de Enrofloxacina L.A. es:" ,0,0,0),
	  (20, "Farmacología y Terapéutica", "La posología de Danofloxacina L.A. es:" ,0,0,0),
	  (21, "Farmacología y Terapéutica", "Para Colibacilosis está/n indicado/s:" ,0,0,0),
	  (22, "Farmacología y Terapéutica", "La/s Vía/s de Administración de las Penicilinas Naturales son:" ,0,0,0),
	  (23, "Farmacología y Terapéutica", "Para Salmonellosis está/n indicado/s:" ,0,0,0),
	  (24, "Farmacología y Terapéutica", "Para Metafilaxia de Ap. Respiratorio está/n indicado/s:" ,0,0,0),
	  (25, "Farmacología y Terapéutica", "Para infecciones oculares está/n indicado/s:" ,0,0,0),
	  (26, "Farmacología y Terapéutica", "La dosis y vía de Pilocarpina es:" ,0,0,0),
	  (27, "Farmacología y Terapéutica", "La dosis y vía de Neostigmina es:" ,0,0,0),
	  (28, "Farmacología y Terapéutica", "La dosis y vía de Atropina es:" ,0,0,0),
	  (29, "Farmacología y Terapéutica", "La dosis y vía de Metescopolamina es:" ,0,0,0),
	  (30, "Farmacología y Terapéutica", "La dosis y vía de Butilescopolamina es:" ,0,0,0),
	  (31, "Farmacología y Terapéutica", "La dosis y vía de Benzetimida es:" ,0,0,0),
	  (32, "Farmacología y Terapéutica", "La dosis y vía de Loperamida es:" ,0,0,0),
	  (33, "Farmacología y Terapéutica", "La dosis de Bicarbonato de Sodio por Vía Oral es:" ,0,0,0),
	  (34, "Farmacología y Terapéutica", "La dosis Total, para un bovino adulto de Dihidroxi Antraquinino es:" ,0,0,0),
	  (35, "Farmacología y Terapéutica", "La dosis de Sulfato de Magnesio pos Vía Oral es:" ,0,0,0),
	  (36, "Farmacología y Terapéutica", "La dosis del Albendazol para nematodos es:" ,0,0,0),
	  (37, "Farmacología y Terapéutica", "La dosis de Albendazol para larvas inhibidas de Ostertagia es:" ,0,0,0),
	  (38, "Farmacología y Terapéutica", "La dosis de Albendazol para Fasciola Hepática es:" ,0,0,0),
	  (39, "Farmacología y Terapéutica", "La dosis de Ivermectina al 1% es:" ,0,0,0),
	  (40, "Farmacología y Terapéutica", "La dosis de Ivermectina al 3,15% es:" ,0,0,0);

INSERT INTO options (id, question_id, description, correct) VALUES
	(0, 1, "Diarrea", 0),
	(0, 1, "Pérdida de peso", 0),
	(0, 1, "Aborto", 0),
	(0, 1, "Las opciones a y b son correctas", 1),
	(0, 2, "Abomaso", 1),
	(0, 2, "Intestino delgado", 0),
	(0, 2, "Intestino grueso", 0),
	(0, 2, "Omaso", 0),
	(0, 3, "Fipronil", 0),
	(0, 3, "Albendazol", 1),
	(0, 3, "Fluazuron", 0),
	(0, 3, "Toltrazuril", 0),
	(0, 4, "Ascitis", 0),
	(0, 4, "Pérdida de peso", 0),
	(0, 4, "Edema", 0),
	(0, 4, "Todas las opciones anteriores son correctas", 1),
	(0, 5, "Albendazol", 0),
	(0, 5, "Rafoxanida", 0),
	(0, 5, "Clorsulon", 0),
	(0, 5, "Todas las opciones anteriores son correctas", 1),
	(0,6,"Hemoncosis",0),
	(0,6,"Hidatidosis",0),
	(0,6,"Cenurosis",0),
	(0,6,"Esofagostomiasis",1),
	(0,7,"Hemoncosis",1),
	(0,7,"Cooperiasis",0),
	(0,7,"Oestrosis",0),
	(0,7,"Dictiocaulosis",0),
	(0,8,"Tos",1),
	(0,8,"Diarrea",0),
	(0,8,"Cólico",0),
	(0,8,"Ataxia",0),
	(0,9,"Ampicilia",0),
	(0,9,"Amprolium",1),
	(0,9,"Closantel",0),
	(0,9,"Rafoxanida",0),
	(0,10,"Fascioliasis",0),
	(0,10,"Ostertagiasis",0),
	(0,10,"Hemoncosis",0),
	(0,10,"Todas las opciones anteriores son correctas",1),
	(0,11,"1,25 a 2,5 mg./Kg. cada 24 Hs. I.M.",0),
	(0,11,"2,5 a 5 mg./Kg. cada 24 Hs. I.M.",1),
	(0,11,"6 mg./Kg. cada 12 Hs. I.M",0),
	(0,11,"7,5 mg./Kg. cada 12 Hs. I.M",0),
	(0,12,"1,25 a 2,5 mg./Kg. cada 24 Hs. I.M.",1),
	(0,12,"2,5 a 5 mg./Kg. cada 24 Hs. I.M.",0),
	(0,12,"10 mg./Kg. cada 24 Hs. I.M",0),
	(0,12,"20 mg./Kg. cada 48 Hs. S.C",0),
	(0,13,"10 mg./Kg. cada 24 Hs. I.M",0),
	(0,13,"20 mg./Kg. cada 48 Hs. I.M",1),
	(0,13,"20 mg./Kg. cada 72 Hs. I.M",0),
	(0,13,"40 mg./Kg. cada 48 Hs. S.C",0),
	(0,14,"7 a 11 mg./Kg. cada 12 Hs. E.V",0),
	(0,14,"10 mg./Kg. cada 24 Hs. I.M",0),
	(0,14,"7,5 mg./Kg. cada 48 Hs. I.M",0),
	(0,14,"20 mg./Kg. cada 72 hs. I.M",1),
	(0,15,"20 mg./Kg. cada 72 Hs. I.M",0),
	(0,15,"7 a 11 mg./Kg. cada 8 a 12 Hs. E.V",1),
	(0,15,"20 mg./Kg. cada 24 Hs. I.M",0),
	(0,15,"15 mg./Kg. cada 48 Hs. S.C",0),
	(0,16,"6 mg./Kg. cada 24 Hs. I.M",0),
	(0,16,"10 mg./Kg. cada 24 Hs.I.M",0),
	(0,16,"15 mg./Kg. cada 48 Hs. I.M",0),
	(0,16,"a y c son correctas",0),
	(0,17,"10 mg./Kg. S.C",0),
	(0,17,"5 mg./Kg. S.C",0),
	(0,17,"20 mg./Kg. I.M",0),
	(0,17,"15 mg./kg. I.M",0),
	(0,18,"10 mg./Kg.S.C",0),
	(0,18,"5 mg./Kg. S.C",1),
	(0,18,"15 mg./Kg. I.M",0),
	(0,18,"20 mg./Kg. I.M",0),
	(0,19,"2,5 mg./Kg. cada 48 Hs",0),
	(0,19,"5 mg./Kg. cada 48 Hs",0),
	(0,19,"7,5 mg./kg. cada 72 Hs",1),
	(0,19,"10 mg./Kg. cada 72 Hs",0),
	(0,20,"2,5 mg./Kg. cada 48 Hs",0),
	(0,20,"6 mg./Kg. cada 72 Hs. I.M",1),
	(0,20,"5 mg./Kg. cada 48 Hs. I.M",0),
	(0,20,"10 mg./Kg. cada 72 Hs. I.M",0),
	(0,21,"Oxitetraciclina",0),
	(0,21,"Enrofloxacina",0),
	(0,21,"Danofloxacina",0),
	(0,21,"Todas las opciones anteriores son correcta",1),
	(0,22,"Penicilina Sódica E.V",0),
	(0,22,"Penicilina Procaínica I.M",0),
	(0,22,"Penicilina Benzatínica I.M",0),
	(0,22,"Todas las opciones anteriores son correctas",1),
	(0,23,"Enrofloxacina",0),
	(0,23,"Danofloxacina",0),
	(0,23,"Florfenicol",0),
	(0,23,"Todas las opciones anteriores son correctas",1),
	(0,24,"Oxitetraciclina L.A",0),
	(0,24,"Tilmicosina",0),
	(0,24,"Enrofloxacina L.A",0),
	(0,24,"Todas las opciones anteriores son correctas",1),
	(0,25,"Oxitetraciclina",0),
	(0,25,"Tilmicosina",0),
	(0,25,"Gentamicina",0),
	(0,25,"Todas las opciones anteriores son correctas",1),
	(0,26,"0,05 a 0,1 mg./Kg. I.M",0),
	(0,26,"0,5 a 1 mg./Kg. S.C",1),
	(0,26,"2,5 a 5 mg./kg. E.V",0),
	(0,26,"2 a 4 mg./Kg. I.M",0),
	(0,27,"0,05 a 0,1 mg./Kg. I.M",1),
	(0,27,"0,5 a 1 mg./Kg. E.V",0),
	(0,27,"2 a 4 mg./Kg. I.M",0),
	(0,27,"1 a 2 mg./Kg. S.C",0),
	(0,28,"0,4 mg./Kg. I.M",0),
	(0,28,"0,08 mg./Kg. S.C",1),
	(0,28,"2 mg./Kg. E.V",0),
	(0,28,"0,5 mg./Kg. S.C",0),
	(0,29,"0,5 mg./Kg. E.V",0),
	(0,29,"0,1 mg./Kg. I.M",1),
	(0,29,"1 mg./Kg. I.M",0),
	(0,29,"2 mg./Kg.S.C",0),
	(0,30,"2 mg./Kg. I.M",0),
	(0,30,"0,2 mg./Kg. E.V",1),
	(0,30,"5 mg./Kg. I.M",0),
	(0,30,"0,5 mg./Kg. S.C",0),
	(0,31,"0,04 mg./Kg. I.M",0),
	(0,31,"0,10 mg./Kg. E.V",0),
	(0,31,"0,015 mg./Kg. I.M",1),
	(0,31,"0,5 mg./Kg. E.V",0),
	(0,32,"1 mg./Kg. S.C",0),
	(0,32,"0,05 mg./Kg. I.M",1),
	(0,32,"0,20 mg./Kg. I.M",0),
	(0,32,"0,15 mg./kg. I.M",0),
	(0,33,"5 a 10 mg./Kg",0),
	(0,33,"0,5 a 1 gr./Kg",1),
	(0,33,"10 a 20 mg./Kg",0),
	(0,33,"2 a 5 gr./Kg",0),
	(0,34,"2 a 4 gr",0),
	(0,34,"5 a 10 gr",0),
	(0,34,"10 a 30 gr",1),
	(0,34,"50 a 100 gr",0),
	(0,35,"2,5 a 5 mg./Kg",0),
	(0,35,"0,5 a 1 gr./Kg",1),
	(0,35,"5 a 10 mgr./Kg",0),
	(0,35,"2 a 4 gr./Kg",0),
	(0,36,"5 mg./Kg",1),
	(0,36,"10 mg./kg",0),
	(0,36,"15 mg./Kg",0),
	(0,36,"20 mg./kg",0),
	(0,37,"5 mg./kg",0),
	(0,37,"7,5 mg./kg",1),
	(0,37,"10 mg./Kg",0),
	(0,37,"12,5 mg./Kg",0),
	(0,38,"5 mg./kg",0),
	(0,38,"7,5 mg./Kg",0),
	(0,38,"10 mg./Kg",1),
	(0,38,"12,5 mg./Kg",0),
	(0,39,"0,10 mg./Kg",0),
	(0,39,"0,20 mg./Kg",1),
	(0,39,"0,315 mg./Kg",0),
	(0,39,"0.630 mg./Kg",0),
	(0,40,"0,10 mg./kg",0),
	(0,40,"0,20 mg./Kg",0),
	(0,40,"0,315 mg./Kg",0),
	(0,40,"0.630 mg./Kg",1);
