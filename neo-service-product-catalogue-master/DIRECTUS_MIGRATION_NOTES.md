# Directus Migration Notes (Prepared)

I added:

1. `product-catalogue-service/src/main/java/com/neo/v1/service/DirectusService.java`
2. Directus config properties in:
   - `product-catalogue-service/src/main/java/com/neo/v1/reader/PropertyReader.java`
   - `product-catalogue-service/src/main/resources/application.yml`

## Current migration status

✅ Offers path switched to Directus:
- `OffersCachingService` now uses `DirectusService` (not `ContentfulService`) for:
  - offers
  - offer categories
  - offer sub-products
- New Directus mappers added:
  - `DirectusOffersListDataMapper`
  - `DirectusOfferDetailsMapper`
  - `DirectusOfferCategoriesMapper`
  - `DirectusSubProductsMapper`

⚠️ Remaining paths still use Contentful mappers/services and should be migrated next:
- products / atm / branches
- competitions
- app branches
- merchants / merchant categories / mcc codes

## Env vars to set

- `DIRECTUS_BASE_URL`
- `DIRECTUS_TOKEN`

Optional overrides:
- `DIRECTUS_COLLECTION_OFFERS`
- `DIRECTUS_COLLECTION_PRODUCTS`
- `DIRECTUS_COLLECTION_ATM`
- `DIRECTUS_COLLECTION_BRANCHES`
- `DIRECTUS_COLLECTION_OFFERS_CATEGORY`
- `DIRECTUS_COLLECTION_OFFERS_SUBPRODUCTS`
- `DIRECTUS_COLLECTION_COMPETITIONS`
- `DIRECTUS_COLLECTION_APP_BRANCHES`
- `DIRECTUS_COLLECTION_MERCHANT_CATEGORY`
- `DIRECTUS_COLLECTION_MERCHANTS`
- `DIRECTUS_COLLECTION_MERCHANT_CODE`

## Suggested migration order

1. Offers path first (service + mapper) and validate.
2. Products/ATM/Branches.
3. Competitions + AppBranches.
4. Merchants/MCC.
5. Remove Contentful SDK usage once all paths are migrated.
