CREATE TABLE "languages" (
  "id" smallint NOT NULL UNIQUE,
  "name" text NOT NULL UNIQUE,
  CONSTRAINT languages_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);

INSERT INTO languages VALUES (1, 'english');
INSERT INTO languages VALUES (2, 'russian');

CREATE TABLE "users" (
  "uuid" uuid NOT NULL UNIQUE,
  "telegram_id" bigint NOT NULL UNIQUE,
  "chat_id" bigint NOT NULL UNIQUE,
  "username" text,
  "first_name" text,
  "last_name" text,
  "language_id" smallint REFERENCES "languages" ("id") ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT users_pk PRIMARY KEY ("uuid")
) WITH (
OIDS=FALSE
);

CREATE TABLE "ad_statuses" (
  "id" smallint NOT NULL UNIQUE,
  "name" text NOT NULL UNIQUE,
  CONSTRAINT ad_statuses_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);

INSERT INTO ad_statuses VALUES (1, 'draft');
INSERT INTO ad_statuses VALUES (2, 'on_sale');
INSERT INTO ad_statuses VALUES (3, 'closed');

CREATE TABLE "ads" (
  "uuid" uuid NOT NULL UNIQUE,
  "user_uuid" uuid NOT NULL NOT NULL REFERENCES "users" ("uuid") ON UPDATE CASCADE ON DELETE CASCADE,
  "status_id" smallint REFERENCES "ad_statuses" ("id") ON UPDATE CASCADE ON DELETE CASCADE,
  "longitude" DOUBLE PRECISION NOT NULL,
  "latitude" DOUBLE PRECISION NOT NULL,
  "address" text NOT NULL,
  "rooms_number" smallint NOT NULL,
  "area" integer NOT NULL,
  "price_per_night" text NOT NULL,
  "photos" text [],
  CONSTRAINT ads_pk PRIMARY KEY ("uuid")
) WITH (
OIDS=FALSE
);

CREATE TABLE "crowd_sales" (
  "uuid" uuid NOT NULL UNIQUE,
  "ad_uuid" uuid NOT NULL NOT NULL REFERENCES "ads" ("uuid") ON UPDATE CASCADE ON DELETE CASCADE,
  "tokens_number" integer NOT NULL,
  "token_price" text NOT NULL,
  "sold_tokens" integer NOT NULL,
  "is_published" boolean NOT NULL,
  "create_date" date NOT NULL,
  "address" text,
  CONSTRAINT crowd_sales_pk PRIMARY KEY ("uuid")
) WITH (
OIDS=FALSE
);