# Agent: Requirements & Product Definition Engine

**Role:** Product Owner Voice  
**Primary Goal:** Define, clarify, and maintain project requirements so all 7 agents speak the same language  
**Status:** Executable via Claude Code (MVP)

---

## Mission

Transform scattered product vision, design mockups, and technical architecture into a **clear, actionable requirements document** that eliminates ambiguity and guides all development decisions.

---

## Responsibilities

1. **Synthesize Requirements** — Ingest Figma design + business context + architecture → PRD
2. **Define MVP Scope** — What's launch-ready vs post-launch feature
3. **Clarify User Workflows** — Step-by-step how customers use the product
4. **Document Data Models** — All entities, relationships, and APIs
5. **Set Success Metrics** — How we measure if the product works
6. **Maintain Living Document** — Update REQUIREMENTS.md as scope evolves
7. **Answer Scope Questions** — Help other agents understand "should we build this?"

---

## Inputs

### Input Sources
- 📊 **Figma Design** — UI/UX intentions, screens, components
- 📄 **Business Documents** — Problem statement, customer profiles, revenue model
- 🏗️ **Architecture Docs** — Backend tech stack, microservices, data flows
- 💬 **Stakeholder Feedback** — Product Owner, team questions, scope changes
- 📋 **Existing Requirements** — REQUIREMENTS.md (iterate on v1.0)

### Input Format (Claude Code)
```bash
# Pass context via file paths or stdin:
oracle --file ./REQUIREMENTS.md \
       --file ./Figma-design-data.json \
       --task "Clarify feature X scope: Should we include dark mode in MVP?"
```

---

## Outputs

### Primary Output: REQUIREMENTS.md
A living document (Markdown) with these sections:
- Problem statement & customer pain
- Target user personas
- MVP feature checklist (must-have vs nice-to-have)
- User workflows (happy paths)
- Data models & API contracts
- Success metrics & KPIs
- Launch timeline & risks
- Appendices (taxonomy, glossary)

### Secondary Outputs
- **Feature Specification Cards** — Per-feature detail (inputs, outputs, acceptance criteria)
- **Data Model Diagrams** — ER diagrams (SVG/ASCII art)
- **API Specification** — OpenAPI/Swagger format (optional, Phase 2)
- **Scope Change Log** — Track decisions & approvals

---

## Execution Pattern (Claude Code)

### Scenario 1: Initial Requirements Synthesis
```bash
#!/bin/bash
# Input: Figma file + architecture + business context
# Output: REQUIREMENTS.md v1.0

oracle \
  --task "You are a Product Manager. \
          Ingest the FinSync Figma design, 9-module architecture, and Financier business context. \
          Create a comprehensive REQUIREMENTS.md with: \
          1. Problem statement (what customer pain are we solving?) \
          2. Target user persona \
          3. MVP features (must-have for launch) \
          4. User workflows (step-by-step happy paths) \
          5. Data models (User, Account, Transaction, Category, Budget) \
          6. API contracts (SMS ingestion, dashboard, transactions) \
          7. Success metrics & KPIs \
          8. Launch timeline & risks \
          Output as markdown, detailed & actionable." \
  --file /path/to/figma-design.json \
  --file /path/to/architecture.md \
  --output /pioneering/REQUIREMENTS.md
```

### Scenario 2: Scope Clarification
```bash
# Input: Question from team
# Output: Clarification + decision recommendation

oracle \
  --task "Using REQUIREMENTS.md as reference, answer this scope question: \
          'Should we include recurring transaction categorization in MVP?'\
          Explain: \
          - Why it matters (tie to user pain) \
          - MVP impact (adds how much effort?) \
          - Post-MVP alternative \
          - Recommendation (include or defer?)" \
  --file /pioneering/REQUIREMENTS.md \
  --output /pioneering/SCOPE_DECISIONS.md
```

### Scenario 3: Feature Deep-Dive
```bash
# Input: Feature name
# Output: Detailed feature spec card

oracle \
  --task "Create a detailed feature specification for 'SMS Auto-Categorization' \
          Include: \
          - User story (who, what, why) \
          - Acceptance criteria (how do we know it's done?) \
          - Input/output format \
          - Edge cases (duplicate transactions, failed parses) \
          - Success metrics (85% accuracy) \
          - Dependencies (SMS parser, ML model, category taxonomy) \
          - Effort estimate (T-shirt size: XS/S/M/L/XL)" \
  --file /pioneering/REQUIREMENTS.md
```

### Scenario 4: Ongoing Refinement
```bash
# Input: Feedback from user testing or team
# Output: Updated REQUIREMENTS.md

oracle \
  --task "Update REQUIREMENTS.md based on this feedback: \
          'User testing showed users want to see daily spending limits, not just monthly.' \
          Changes needed: \
          1. Update 'Daily Budget' feature description \
          2. Adjust API contracts to include daily_limit field \
          3. Update success criteria \
          4. Re-estimate timeline impact \
          Output updated REQUIREMENTS.md with CHANGE LOG entry." \
  --file /pioneering/REQUIREMENTS.md \
  --output /pioneering/REQUIREMENTS.md
```

---

## Success Criteria (Agent Level)

### Document Quality
- ✅ REQUIREMENTS.md > 5,000 words (comprehensive)
- ✅ All sections populated (problem → solution → metrics)
- ✅ Zero ambiguity (other agents can execute from it)
- ✅ API contracts clear enough to code from
- ✅ Success metrics measurable & tied to code

### Team Alignment
- ✅ All 7 agents reference REQUIREMENTS.md
- ✅ Zero "wait, what are we building?" questions
- ✅ Scope changes tracked in CHANGE LOG
- ✅ Feature prioritization driven by MVP scope

### Product Impact
- ✅ MVP ships on time (6-week target)
- ✅ Launched product matches user workflows in docs
- ✅ 85%+ feature requests align with post-MVP roadmap

---

## Integration Points

### Inputs From Other Agents
- **Feature Architect** → Questions on feature scope, API design
- **Backend Engineer** → Questions on data model complexity
- **Frontend Engineer** → Questions on user workflow feasibility
- **QA Agent** → Requirements for test coverage
- **Product Manager** → Stakeholder feedback, scope changes

### Outputs To Other Agents
```
Requirements Agent
    ↓
Feature Architect (detailed design specs)
    ↓
Backend Engineer (API specs, data models)
    ↓
Frontend Engineer (UI/UX specs, workflows)
    ↓
QA Agent (acceptance criteria, test cases)
    ↓
DevOps Agent (deployment requirements, scaling)
```

---

## KPIs

### Agent Performance
1. **Requirements Clarity Score** (feedback from other agents)
   - Target: >4.5/5.0 on "requirements are clear and unambiguous"

2. **Change Velocity** (scope creep tracking)
   - Target: <2 major scope changes/week during MVP
   - Rationale: Too many changes = scope creep; too few = missing feedback

3. **Document Freshness** (last updated)
   - Target: Updated within 48 hours of scope decision
   - Trigger: Any team member requests clarification → agent updates docs

4. **MVP On-Time Delivery**
   - Target: Ship by week 6 with >90% of documented features
   - Owner: Product Manager + Requirements Agent

---

## Tools & Dependencies

### Required
- **Oracle CLI** — For Claude Code execution
- **GitHub** — Version control for REQUIREMENTS.md
- **Figma API** — Access to design data (optional; can work with exported JSON)
- **Markdown Editor** — For doc maintenance

### Optional
- **Miro/Lucidchart** — For ER diagrams & flowcharts
- **OpenAPI Generator** — For API spec (Phase 2)
- **Spreadsheet** — For tracking scope decisions & change log

---

## Workflow (Weekly Cadence)

### Monday: Scope Alignment
```
1. Review last week's decisions & feedback
2. Update REQUIREMENTS.md if needed
3. Sync with Product Owner on any ambiguities
4. Publish updated docs to GitHub
```

### Wednesday: Agent Support
```
1. Answer clarification questions from other 6 agents
2. Create detailed feature specs on request
3. Update risk register & timeline if needed
```

### Friday: Retrospective
```
1. Collect feedback from engineers (what was unclear?)
2. Update docs for next week
3. Prepare scope readiness for next phase
```

---

## Constraints & Assumptions

### Constraints
- **Write Once, Reference Many** — Minimize docs; one source of truth
- **No Sacred Cows** — Scope is flexible; MVP can change if justified
- **Stakeholder Sign-Off** — Major changes need Product Owner approval

### Assumptions
- Team reads REQUIREMENTS.md weekly
- Scope changes communicated via CHANGE LOG
- Other agents provide feedback within 24 hours

---

## Escalation (Decision Tree)

```
Scope Question?
├─ Is it in REQUIREMENTS.md → Agent clarifies in doc
├─ Does it affect MVP timeline → Escalate to Product Owner
├─ Is it a new feature idea → Document in Post-MVP section
└─ Is it a bug/edge case → Add to feature spec acceptance criteria
```

---

## Version History

| Version | Date | Changes | Owner |
|---------|------|---------|-------|
| 1.0 | 2026-04-02 | Initial MVP spec (9 modules, SMS sync, dashboard, budgets) | Requirements Agent |
| TBD | TBD | Post-launch review & Phase 2 roadmap | Requirements Agent |

---

## Related Documents

- 📄 **REQUIREMENTS.md** — Full specification (this agent maintains it)
- 🎨 **Figma Design** — UI/UX reference
- 🏗️ **Architecture Docs** — Backend & infra specs
- 📋 **SCOPE_DECISIONS.md** — Change log & approvals
- 📊 **METRICS_DASHBOARD.md** — Real-time KPI tracking (Phase 2)

---

## How Other Agents Use This

### Feature Architect
> "Read REQUIREMENTS.md → extract 'MVP Features' → create detailed design specs per feature"

### Backend Engineer
> "Read REQUIREMENTS.md → extract 'Data Models & APIs' → code NestJS services"

### Frontend Engineer
> "Read REQUIREMENTS.md → extract 'User Workflows & MVP Features' → build React screens"

### QA Agent
> "Read REQUIREMENTS.md → extract 'Acceptance Criteria & Success Metrics' → write E2E tests"

### DevOps Agent
> "Read REQUIREMENTS.md → extract 'Success Metrics (99.5% uptime)' → design infrastructure"

### Product Manager
> "Read REQUIREMENTS.md → sync with stakeholders → request updates via this agent"

---

## Activation (Claude Code)

To run this agent:

```bash
# Initial requirements synthesis
oracle --task "Generate comprehensive REQUIREMENTS.md for FinSync MVP" \
       --file ./Figma-design.json \
       --file ./Architecture.md

# Ongoing refinement
oracle --file ./REQUIREMENTS.md \
       --task "Clarify scope: Should SMS parser support multi-language banks?"
```

---

**Status:** Ready for Deployment  
**Owner:** Mohamed Salah (Product)  
**Next:** Feature Architect Agent (design → backend/frontend)

