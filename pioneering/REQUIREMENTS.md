# FinSync Financial Management Platform — Requirements & Product Definition

**Version:** 1.0  
**Last Updated:** 2026-04-02  
**Status:** MVP Specification  

---

## 1. Problem Statement

### The Customer Problem
Users struggle to manage their personal finances effectively because:

1. **Manual Data Entry Burden** — Adding every transaction manually is tedious and error-prone
2. **Loss of Transaction Visibility** — Bank SMS notifications get lost; no central view of spending
3. **No Automated Categorization** — Users can't quickly understand where their money goes
4. **Siloed Accounts** — Multiple banks/wallets create fragmented financial picture
5. **Zero Budgeting Insights** — No real-time feedback on overspending vs. goals
6. **Debt & Savings Disconnection** — Multiple financial goals feel unmanageable

### The FinSync Solution
An **AI-powered personal finance dashboard** that:
- **Auto-ingests transactions** via bank SMS parsing (eliminates manual entry)
- **Intelligently categorizes** spending with ML
- **Unifies accounts** (multiple banks/wallets in one view)
- **Provides real-time insights** on spending patterns and budget health
- **Tracks goals** (savings, debt payoff, budgets) in one place
- **Enables quick actions** (transfer, categorize, set alerts)

---

## 2. Target Customer

### Primary User Persona
- **Name:** Ahmed (28), Middle East professional
- **Income:** SAR 8,000–15,000/month
- **Financial Habits:** Multiple bank accounts, receives transaction SMSs, manually tracks spending in spreadsheets
- **Pain Point:** Overwhelmed by manual tracking; wants to know where money goes without constant data entry
- **Tech Savviness:** Comfortable with mobile-first apps, expects seamless experience
- **Goal:** Reduce financial anxiety, optimize spending, build emergency fund

### Secondary Persona
- **Young Families** — Managing household budget across multiple accounts
- **Entrepreneurs/Freelancers** — Tracking income + business expenses

---

## 3. Core Value Proposition

**"Know where your money goes—without the paperwork."**

- 🤖 **Automatic** — Bank SMS → instant transaction sync
- 📊 **Clear** — One-screen view of all accounts, spending, and goals
- 🎯 **Smart** — AI categorizes transactions; highlights overspending in real-time
- 🔒 **Secure** — No bank login required; SMS-only data ingestion

---

## 4. MVP Scope (Launch Feature Set)

### 4.1 Must-Have Features (MVP)

#### A. Account & Wallet Management
- [ ] Link multiple bank accounts (manual entry + SMS sync)
- [ ] Display wallet/account balances
- [ ] Transaction history per account
- [ ] Manual transaction entry (backup if SMS fails)

#### B. Automatic SMS Transaction Parsing
- [ ] Bank SMS listener (receive, parse, classify)
- [ ] Extract: amount, merchant, timestamp, account
- [ ] Auto-categorize using predefined rules + ML
- [ ] Deduplicate transactions (prevent double-entries)
- [ ] Real-time sync to dashboard

#### C. Spending Insights Dashboard
- [ ] **Total Net Worth** — Sum of all account balances
- [ ] **This Month's Spending** — Total expenses vs budget
- [ ] **Income vs Expenses** — Monthly trend chart (last 6 months)
- [ ] **Expense Breakdown** — Categories (Housing, Food, Transport, Other, Entertainment, Utilities)
- [ ] **Quick Insights** — "You've spent 40% of budget this month" / "You overspent on Food by SAR 200"

#### D. Budget Management
- [ ] Set budget per category (monthly)
- [ ] Track remaining budget in real-time
- [ ] Alert when category nears limit
- [ ] Budget performance summary (on-track vs over-budget)

#### E. User Authentication & Settings
- [ ] Phone-based signup (mobile number)
- [ ] SMS OTP verification
- [ ] Profile settings (name, currency, language)
- [ ] Category preferences (customize categories)
- [ ] Notification settings (SMS/push alerts)

#### F. Mobile-First UI (Per Figma Design)
- [ ] **Home Screen** — Net worth, budget status, recent transactions
- [ ] **Wallet Screen** — Account management, add account, manual entry
- [ ] **Reports Screen** — Spending charts, insights, trends
- [ ] **Settings Screen** — User profile, categories, notifications

### 4.2 MVP Success Criteria
- ✅ User can link 2+ bank accounts via SMS sync
- ✅ Transactions appear within 5 minutes of bank SMS
- ✅ At least 85% of transactions auto-categorized correctly
- ✅ User can set budget and see real-time status
- ✅ Dashboard loads in <2 seconds
- ✅ 99.5% uptime (target: <1 hour unplanned downtime/month)

---

## 5. Post-MVP Features (Future Roadmap)

### Phase 2 (Months 3–4)
- [ ] Recurring transactions & subscription tracking
- [ ] Savings goals with progress visualization
- [ ] Debt tracking (loans, credit cards, EMIs)
- [ ] Multi-user household budgeting
- [ ] Advanced charts (pie, waterfall, forecast)

### Phase 3 (Months 5–6)
- [ ] Bank API integration (remove SMS dependency)
- [ ] Mobile app (iOS/Android native)
- [ ] Export reports (PDF, CSV)
- [ ] Dark mode & custom themes

### Phase 4 (Post-Launch)
- [ ] AI spending recommendations
- [ ] Wealth management tools
- [ ] Investment tracking
- [ ] Partner integrations (insurance, investments)

---

## 6. User Workflows (Happy Paths)

### Workflow 1: Onboarding (Day 1)
```
User installs app
    → Signup with phone number
    → Enter OTP
    → Add first bank account (SMS)
    → System receives test SMS
    → Dashboard shows first transaction
    → Set budget for key categories
    → Ready to use
```

### Workflow 2: Daily Transaction Sync
```
User receives bank SMS: "Debit: SAR 150 from Starbucks"
    → FinSync SMS listener captures it
    → System extracts amount, merchant, timestamp
    → ML classifier → "Coffee Shop" → "Food & Dining" category
    → Dashboard updates in real-time
    → User sees "Food budget: 65% used" alert (if near limit)
```

### Workflow 3: Monthly Budget Review
```
User opens "Reports" screen on day 25 of month
    → Sees income vs expenses chart
    → Spending breakdown by category (pie chart)
    → 3 categories flagged as over-budget (red)
    → Insight: "You've spent 45% more on Transport this month"
    → User can adjust next month's budget
```

### Workflow 4: Emergency Overspend
```
User spending reaches 95% of monthly budget (2 weeks early)
    → System sends alert SMS + push notification
    → Dashboard shows warning: "Budget limit approaching"
    → User can temporarily increase budget or review spending
```

---

## 7. Data Models & Core Entities

### User
```
{
  userId (UUID)
  phoneNumber (E.164)
  email (optional)
  currency (SAR, AED, USD, etc.)
  createdAt
  settings { language, timezone, notifications }
}
```

### Account / Wallet
```
{
  accountId (UUID)
  userId (FK)
  accountType (bank, credit_card, cash, savings)
  bank (AlAhli, ADIB, FAB, etc.)
  accountName (e.g., "Main Checking")
  currentBalance
  lastSyncTime
  syncStatus (active, inactive)
}
```

### Transaction
```
{
  transactionId (UUID)
  accountId (FK)
  amount (decimal)
  currency (SAR, etc.)
  merchant (string)
  category (categoryId FK)
  type (debit, credit)
  timestamp
  source (sms_auto, manual_entry, api)
  status (processed, pending, failed)
  bankReference (optional, for deduplication)
}
```

### Category
```
{
  categoryId (UUID)
  userId (FK)
  name (e.g., "Food & Dining")
  icon (string)
  color (hex)
  isDefault (boolean)
}
```

### Budget
```
{
  budgetId (UUID)
  userId (FK)
  categoryId (FK)
  amount (decimal, monthly limit)
  month (YYYY-MM)
  status (active, completed, paused)
}
```

### SMS Event (Raw)
```
{
  smsEventId (UUID)
  userId (FK)
  accountId (FK, inferred)
  rawSmsText (string)
  parsedData { amount, merchant, timestamp, type }
  confidence (0–1, ML score)
  status (parsed, failed)
  createdAt
}
```

---

## 8. API & Integration Contracts

### SMS Ingestion (Incoming)
**Trigger:** Bank SMS arrives at user's phone
```
POST /sms/webhook
{
  "phoneNumber": "+966501234567",
  "smsText": "Debit: SAR 250.00 from STARBUCKS RIYADH on 02-APR-26 at 14:30",
  "timestamp": "2026-04-02T14:30:00Z"
}

Response:
{
  "status": "processed",
  "transactionId": "txn_abc123",
  "category": "food_dining",
  "confidence": 0.95
}
```

### Get Dashboard Summary
```
GET /dashboard/summary?userId=user_123&period=month

Response:
{
  "totalBalance": 45000,
  "monthlyIncome": 12000,
  "monthlyExpenses": 8500,
  "budgetStatus": [
    { "category": "Food", "budgeted": 2000, "spent": 1800, "status": "on_track" },
    { "category": "Transport", "budgeted": 1500, "spent": 1650, "status": "over_budget" }
  ],
  "topInsights": [
    "You spent 45% more on transport this month"
  ]
}
```

### Get Transactions
```
GET /transactions?accountId=acc_123&month=2026-04&limit=50

Response:
[
  {
    "transactionId": "txn_abc123",
    "amount": 250,
    "merchant": "STARBUCKS RIYADH",
    "category": "food_dining",
    "timestamp": "2026-04-02T14:30:00Z",
    "type": "debit"
  }
]
```

---

## 9. Technical Architecture (Backend)

### Tech Stack
- **Framework:** NestJS (Node.js, TypeScript)
- **Database:** PostgreSQL
- **Authentication:** JWT, SMS OTP
- **Messaging:** Redis (queue), SMS gateway (Twilio/local)
- **Containerization:** Docker
- **Orchestration:** Kubernetes
- **API Gateway:** Kong or NGINX
- **Logging:** ELK or CloudWatch
- **CI/CD:** GitHub Actions

### 9 Core Modules (NestJS)
1. **User Management** — Auth, profile, settings
2. **Account/Wallet** — Bank account linking, balances
3. **Transaction** — Transaction storage, CRUD
4. **SMS Sync** — SMS ingestion, parsing, deduplication
5. **Category** — Category management, default taxonomy
6. **Budget** — Budget definition, tracking, alerts
7. **Insights** — Dashboard data, trends, notifications
8. **Notification** — SMS/push alerts
9. **Settings** — Currency, language, integrations

---

## 10. Success Metrics (Post-Launch)

### User Metrics
- **DAU (Daily Active Users):** >10K within 3 months
- **Monthly Transactions Processed:** >500K by month 3
- **Transaction Auto-Categorization Accuracy:** >90%
- **User Retention (Day 30):** >50%
- **User Retention (Day 90):** >30%

### Product Metrics
- **Avg Transactions/User/Month:** 30+
- **SMS Parse Latency:** <2 seconds (p95)
- **API Response Time:** <500ms (p95)
- **System Uptime:** 99.5%

### Business Metrics
- **Cost Per User Acquisition:** <SAR 50
- **Monthly Recurring Revenue (freemium):** ~SAR 5K by month 6
- **Premium Conversion Rate:** >5%

---

## 11. MVP Launch Timeline

| Phase | Duration | Deliverable |
|-------|----------|-------------|
| **Design & Requirements** | Week 1 | This document + UI finalization |
| **Backend Development** | Weeks 2–4 | Core APIs, SMS parser, DB schema |
| **Frontend Development** | Weeks 2–4 | Mobile UI, integration with backend |
| **SMS Integration** | Week 3 | Bank SMS listener, parser, deduplication |
| **QA & Testing** | Weeks 4–5 | E2E tests, load testing, security audit |
| **Staging Deployment** | Week 5 | Full-stack staging environment |
| **Launch** | Week 6 | Production rollout, monitoring |

**MVP Ready By:** Week 6 (~6 weeks from start)

---

## 12. Constraints & Assumptions

### Assumptions
- Users have SMS-enabled bank accounts (majority have)
- Transactions are debits/credits to personal accounts
- SMS format is consistent per bank (can build parsers)
- Users trust FinSync with transaction data (privacy is priority)

### Constraints
- **No Direct Bank APIs** (MVP) — Relies on SMS only (faster to market)
- **Single Currency per User** (MVP) — Multi-currency in Phase 2
- **Web-Only Launch** — Mobile app in Phase 2
- **Manual Category Setup** (MVP) — Uses defaults; users customize later

### Security Assumptions
- All SMS data encrypted in transit (TLS) and at rest
- No bank credentials stored (SMS-only model)
- User authentication via phone (not email)
- PCI-DSS compliance not required (no card data stored)

---

## 13. Risk Mitigation

| Risk | Probability | Impact | Mitigation |
|------|-------------|--------|------------|
| Bank SMS format changes | Medium | High | Build adaptive parser; manual fallback |
| SMS parser accuracy issues | Medium | Medium | ML model v0; human review + feedback loop |
| User adoption (depends on SMS marketing) | Medium | High | Referral program + social proof |
| High SMS processing latency | Low | Medium | Use queue (Redis) + async processing |
| Database scalability (500K+ txns/month) | Low | High | Partition by userId; read replicas |

---

## 14. Approval & Sign-Off

- **Product Owner:** Mohamed Salah
- **Created:** 2026-04-02
- **Version:** 1.0 (MVP Specification)

**Next Step:** Assign to Feature Architect Agent for detailed design breakdown → Backend Engineer → Frontend Engineer → QA

---

## Appendix A: Category Taxonomy (MVP)

```
Food & Dining
  - Restaurants
  - Coffee & Cafes
  - Groceries

Transport
  - Fuel
  - Uber/Taxi
  - Parking

Entertainment
  - Movies & Streaming
  - Gaming
  - Events

Shopping
  - Clothing
  - Electronics
  - Books

Utilities
  - Electricity
  - Water
  - Internet

Healthcare
  - Pharmacy
  - Doctor
  - Insurance

Other
```

---

**End of Requirements Document**
