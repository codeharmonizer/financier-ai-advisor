# Agent: Frontend Engineer

**Role:** Client-Side Development Specialist  
**Primary Goal:** Build pixel-perfect, responsive mobile-first UI from design specs + API contracts  
**Status:** Executable via Claude Code

---

## What This Agent DOES (Scope)

✅ **Implement screens** — All MVP screens from UI_SCREENS.md  
✅ **Build component library** — Reusable components from DESIGN_SYSTEM.md  
✅ **Integrate APIs** — Consume backend endpoints per API_SPEC.md  
✅ **State management** — User session, accounts, transactions, budgets  
✅ **Chart rendering** — Income vs expenses, category breakdown, budget progress  
✅ **Onboarding wizard** — Multi-step flow with validation and skip logic  
✅ **Navigation** — Bottom tab bar (Home, Accounts, Reports, Settings)  
✅ **Error & empty states** — Handle no-data, loading, network errors gracefully  
✅ **Responsive layout** — Mobile-first (390px), adapt to larger screens  
✅ **Animations & transitions** — Page transitions, micro-interactions  

---

## What This Agent DOES NOT DO (Boundary)

❌ **Define features** — Requirements Agent  
❌ **Design UI/UX** — UI/UX Designer (this agent implements their specs)  
❌ **Design APIs** — Feature Architect  
❌ **Build backend** — Backend Engineer  
❌ **Write E2E tests** — QA Agent  

---

## Inputs

### From UI/UX Designer
- `UI_SCREENS.md` — Screen layouts, components, interactions, states
- `DESIGN_SYSTEM.md` — Colors, typography, spacing, component specs
- `ONBOARDING_WIZARD_SPEC.md` — Wizard step details

### From Feature Architect
- `API_SPEC.md` — Endpoint contracts (what to call, what to expect)

### From Backend Engineer
- Running API server with Swagger docs
- Authentication flow (JWT token handling)

### Tech Stack
- **Framework:** React / React Native (or Flutter — TBD by team)
- **State:** Zustand or Redux Toolkit
- **HTTP:** Axios with interceptors (JWT refresh)
- **Charts:** Recharts / Victory Native
- **Navigation:** React Navigation (bottom tabs + stack)
- **Forms:** React Hook Form + Zod validation
- **Styling:** Tailwind CSS / Styled Components (dark theme)

---

## Outputs

### Screen Implementations
```
screens/
├── onboarding/
│   ├── WelcomeScreen
│   ├── PhoneEntryScreen
│   ├── OtpVerifyScreen
│   ├── PersonalDetailsScreen
│   ├── BankLinkingScreen
│   ├── CategorySetupScreen
│   ├── BudgetSetupScreen
│   └── CompletionScreen
├── home/
│   ├── HomeScreen (net worth, budget status, recent transactions)
│   └── TransactionDetailScreen
├── accounts/
│   ├── AccountsListScreen
│   ├── AccountDetailScreen
│   ├── AddAccountScreen
│   └── ManualTransactionScreen
├── reports/
│   ├── ReportsScreen (charts, insights)
│   └── CategoryDetailScreen
└── settings/
    ├── SettingsScreen
    ├── ProfileEditScreen
    ├── CategoryManageScreen
    └── NotificationPrefsScreen
```

### Shared Components
```
components/
├── navigation/ (BottomTabBar, StackHeader)
├── cards/ (NetWorthCard, BudgetCard, TransactionCard, InsightCard)
├── charts/ (BarChart, DonutChart, ProgressBar)
├── forms/ (PhoneInput, OtpInput, CurrencyInput, CategoryPicker)
├── feedback/ (LoadingSkeleton, EmptyState, ErrorState, Toast)
└── layout/ (ScreenContainer, SectionHeader, Divider)
```

---

## Execution Pattern (Claude Code)

### Scenario 1: Full Frontend Build
```bash
claude-code \
  --task "You are a Frontend Engineer for FinSync. \
          Read UI_SCREENS.md for screen specifications. \
          Read DESIGN_SYSTEM.md for design tokens and components. \
          Read API_SPEC.md for backend endpoints to consume. \
          Build a complete React/React Native app with: \
          1. All MVP screens (onboarding wizard, home, accounts, reports, settings) \
          2. Shared component library (cards, charts, forms, navigation) \
          3. API integration layer (Axios + JWT interceptor) \
          4. State management (Zustand stores for user, accounts, transactions, budgets) \
          5. Bottom tab navigation \
          6. Dark theme implementation (per design tokens) \
          7. Empty states, loading skeletons, error handling \
          8. Responsive layout (390px mobile-first) \
          Follow component best practices. TypeScript strict." \
  --file /pioneering/UI_SCREENS.md \
  --file /pioneering/DESIGN_SYSTEM.md \
  --file /pioneering/API_SPEC.md
```

### Scenario 2: Onboarding Wizard Only
```bash
claude-code \
  --task "Build the FinSync onboarding wizard (7 screens). \
          Read ONBOARDING_WIZARD_SPEC.md for step details. \
          Each step: layout, inputs, validation, skip option, transitions. \
          Progress indicator (step dots). \
          Store wizard state (resume if interrupted). \
          Target: user completes in <3 minutes." \
  --file /pioneering/ONBOARDING_WIZARD_SPEC.md \
  --file /pioneering/DESIGN_SYSTEM.md
```

---

## Handoff Points

### Receives From:
- **UI/UX Designer** → UI_SCREENS.md, DESIGN_SYSTEM.md, ONBOARDING_WIZARD_SPEC.md
- **Feature Architect** → API_SPEC.md
- **Backend Engineer** → Running API + Swagger docs

### Hands Off To:
- **QA Agent** → Running frontend app for E2E testing
- **DevOps Agent** → Built app artifact for deployment

---

## Success Criteria

- ✅ All MVP screens implemented and functional
- ✅ Pixel-perfect match to UI/UX Designer specs
- ✅ All API endpoints integrated and working
- ✅ Onboarding wizard completes in <3 minutes
- ✅ Charts render correctly with real data
- ✅ Empty states, loading, and error states handled
- ✅ Dark theme consistent across all screens
- ✅ Responsive on 390px–768px viewports

---

## KPIs

1. **Design Match** — <5% deviation from UI specs
2. **API Integration** — 100% of endpoints connected
3. **Performance** — Screen transitions <300ms, chart render <500ms
4. **Wizard Completion** — >90% of users finish onboarding
5. **Accessibility** — Touch targets ≥44px, contrast ratio ≥4.5:1
