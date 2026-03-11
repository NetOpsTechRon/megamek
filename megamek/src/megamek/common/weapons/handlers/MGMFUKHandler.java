/*
 * Copyright (C) 2004, 2005 Ben Mazur (bmazur@sev.org)
 * Copyright (C) 207-2025 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPL),
 * version 3 or (at your option) any later version,
 * as published by the Free Software Foundation.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * A copy of the GPL should have been included with this project;
 * if not, see <https://www.gnu.org/licenses/>.
 *
 * NOTICE: The MegaMek organization is a non-profit group of volunteers
 * creating free software for the BattleTech community.
 *
 * MechWarrior, BattleMech, `Mech and AeroTech are registered trademarks
 * of The Topps Company, Inc. All Rights Reserved.
 *
 * Catalyst Game Labs and the Catalyst Game Labs logo are trademarks of
 * InMediaRes Productions, LLC.
 *
 * MechWarrior Copyright Microsoft Corporation. MegaMek was created under
 * Microsoft's "Game Content Usage Rules"
 * <https://www.xbox.com/en-US/developers/rules> and it is not endorsed by or
 * affiliated with Microsoft.
 */

package megamek.common.weapons.handlers;

import static java.lang.Math.floor;

import java.io.Serial;
import java.util.Vector;

import megamek.common.RangeType;
import megamek.common.Report;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.compute.Compute;
import megamek.common.equipment.AmmoMounted;
import megamek.common.equipment.ArmorType;
import megamek.common.equipment.EquipmentType;
import megamek.common.game.Game;
import megamek.common.loaders.EntityLoadingException;
import megamek.common.options.OptionsConstants;
import megamek.common.rolls.TargetRoll;
import megamek.common.units.Entity;
import megamek.common.units.IBuilding;
import megamek.common.units.Infantry;
import megamek.common.weapons.DamageType;
import megamek.server.totalWarfare.TWGameManager;

/**
 * @author Andrew Hunter Created on Oct 20, 2004
 */
public class MGMFUKHandler extends AmmoWeaponHandler {
    @Serial
    private static final long serialVersionUID = 5637871269404561702L;

    private int nRapidDamHeatPerHit;

    /**
     *
     */
    public MGMFUKHandler(ToHitData t, WeaponAttackAction w, Game g, TWGameManager m) throws EntityLoadingException {
        super(t, w, g, m);
        damageType = DamageType.ANTI_INFANTRY;
    }

    /*
     * (non-Javadoc)
     *
     * @see megamek.common.weapons.handlers.WeaponHandler#calcDamagePerHit()
     */
    @Override
    protected void handleEntityDamage(Entity entityTarget, Vector<Report> vPhaseReport, IBuilding bldg, int hits,
          int nCluster, int bldgAbsorbs) {
        if (game.getOptions().booleanOption(OptionsConstants.PLAYTEST_3)) {
            if (hit != null) {
                hit.setHeatWeapon(true);
            }
        }
        super.handleEntityDamage(entityTarget, vPhaseReport, bldg, hits, nCluster, bldgAbsorbs);
        if (!missed && entityTarget.tracksHeat()) {
            Report report = new Report(3400);
            report.subject = subjectId;
            report.indent(2);
            int extraHeat = 0;
            // if this is a fighter squadron, we need to account for the number of weapons should default to one for
            // non-squadrons
            for (int i = 0; i < numWeaponsHit; i++) {
                extraHeat += (2);
            }

            if (entityTarget.getArmor(hit) > 0
                  &&
                  (entityTarget.getArmorType(hit.getLocation()) == EquipmentType.T_ARMOR_REFLECTIVE)
                  && !game.getOptions().booleanOption(OptionsConstants.PLAYTEST_3)) {
                // PLAYTEST3 do not halve for reflective
                entityTarget.heatFromExternal += Math.max(1, extraHeat / 2);
                report.add(Math.max(1, extraHeat / 2));
                report.choose(true);
                report.messageId = 3406;
                report.add(extraHeat);
                report.add(ArmorType.forEntity(entityTarget, hit.getLocation()).getName());
            } else if (entityTarget.getArmor(hit) > 0 &&
                  (entityTarget.getArmorType(hit.getLocation()) == EquipmentType.T_ARMOR_HEAT_DISSIPATING)) {
                if (game.getOptions().booleanOption(OptionsConstants.PLAYTEST_3)) {
                    // PLAYTEST3 no heat from plasma
                    extraHeat = 0;
                }
                entityTarget.heatFromExternal += extraHeat / 2;
                report.add(extraHeat / 2);
                report.choose(true);
                report.messageId = 3406;
                report.add(extraHeat);
                report.add(ArmorType.forEntity(entityTarget, hit.getLocation()).getName());
            } else {
                entityTarget.heatFromExternal += extraHeat;
                report.add(extraHeat);
                report.choose(true);
            }
            vPhaseReport.addElement(report);
        }
    }

    @Override
    protected int calcDamagePerHit() {
        double toReturn = nDamPerHit;
        if (weapon.isRapidFire() && !(target.isConventionalInfantry())) {
            // Check for rapid fire Option. Only MGs can be rapid fire.
            // nDamPerHit was already set in useAmmo
            toReturn = applyGlancingBlowModifier(toReturn, false);

            if (bDirect) {
                toReturn = Math.min(toReturn + (int) floor(toHit.getMoS() / 3.0),
                      toReturn * 2);
            }
        } else {
            if (target.isConventionalInfantry()) {
                toReturn = Compute.directBlowInfantryDamage(
                      weaponType.getDamage(), bDirect ? toHit.getMoS() / 3 : 0,
                      weaponType.getInfantryDamageClass(),
                      ((Infantry) target).isMechanized(),
                      toHit.getThruBldg() != null, weaponEntity.getId(), calcDmgPerHitReport);

                toReturn = applyGlancingBlowModifier(toReturn, true);
            } else {
                toReturn = weaponType.getDamage();
                if (bDirect) {
                    toReturn = Math.min(toReturn + (int) floor(toHit.getMoS() / 3.0),
                          toReturn * 2);
                }

                toReturn = applyGlancingBlowModifier(toReturn, false);
            }
        }
        if (game.getOptions().booleanOption(OptionsConstants.ADVANCED_COMBAT_TAC_OPS_RANGE)
              && (nRange > weaponType.getRanges(weapon)[RangeType.RANGE_LONG])) {
            toReturn *= .75;
            toReturn = (int) Math.floor(toReturn);
        }
        if (game.getOptions().booleanOption(OptionsConstants.ADVANCED_COMBAT_TAC_OPS_LOS_RANGE)
              && (nRange > weaponType.getRanges(weapon)[RangeType.RANGE_EXTREME])) {
            toReturn = (int) Math.floor(toReturn * .5);
        }
        nDamPerHit = (int) toReturn;

        return nDamPerHit;
    }

    /*
     * (non-Javadoc)
     *
     * @see megamek.common.weapons.handlers.WeaponHandler#addHeat()
     */
    @Override
    protected void addHeat() {
        if (!(toHit.getValue() == TargetRoll.IMPOSSIBLE)) {
            if (weapon.isRapidFire()) {
                weaponEntity.heatBuildup += nRapidDamHeatPerHit;
            } else {
                super.addHeat();
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see megamek.common.weapons.handlers.WeaponHandler#reportMiss(java.util.Vector)
     */
    @Override
    protected void reportMiss(Vector<Report> vPhaseReport) {
        // Report the miss
        Report r = new Report(3220);
        r.subject = subjectId;
        vPhaseReport.add(r);
        if (weapon.isRapidFire() && !target.isConventionalInfantry()) {
            r.newlines = 0;
            r = new Report(3225);
            r.subject = subjectId;
            r.add(nDamPerHit * 3);
            vPhaseReport.add(r);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see megamek.common.weapons.handlers.WeaponHandler#useAmmo()
     */
    @Override
    protected void useAmmo() {
        if (weapon.isRapidFire()) {

            // TacOps p.102 Rapid Fire MG Rules
            switch (weaponType.getAmmoType()) {
                case MG:
                    nDamPerHit = Compute.d6();
                    break;
                case MG_HEAVY:
                    nDamPerHit = Compute.d6() + 1;
                    break;
                case MG_LIGHT:
                    nDamPerHit = Math.max(1, Compute.d6() - 1);
                    break;
                default:
                    // This should not happen for rapid fire MGs, but handle gracefully
                    nDamPerHit = 1;
                    break;
            }

            numRapidFireHits = nDamPerHit;
            nRapidDamHeatPerHit = nDamPerHit;
            checkAmmo();
            int ammoUsage = 3 * nRapidDamHeatPerHit;
            for (int i = 0; i < ammoUsage; i++) {
                if (ammo.getUsableShotsLeft() <= 0) {
                    weaponEntity.loadWeapon(weapon);
                    ammo = (AmmoMounted) weapon.getLinked();
                }
                ammo.setShotsLeft(ammo.getBaseShotsLeft() - 1);
            }
            setDone();
        } else {
            super.useAmmo();
        }
    }

}
