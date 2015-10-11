/*
 * The MIT License
 *
 * Copyright 2015 Andrew Vitkus, Skye Frame.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package dungeonBuilder.util;

import dungeonBuilder.space.ICompositeSpace;
import dungeonBuilder.space.IPoint;
import dungeonBuilder.space.ISpace;
import java.util.Arrays;

/**
 *
 * @author Andrew Vitkus
 */
public class SpaceDeclutterer {
    private static final double REPULSION_FORCE = 1;
    private static final int BORDER_WIDTH = 10;
    
    public static void process(ISpace[] spaces) {
        boolean doContinue;
        do {
            doContinue = false;
            for(int i = 0; i < spaces.length; i++) {
                for(int j = 0; j < spaces.length; j ++) {
                    if (i==j) {
                        j ++;
                        if (j == spaces.length) {
                            break;
                        }
                    }
                    if (doesOverlap(spaces[i], spaces[j])) {
                        ISpace[] otherSpaces = new ISpace[spaces.length - 1];
                        int writeLoc = 0;
                        for(int k = 0; k < spaces.length; k ++) {
                            if (i == k) {
                                k ++;
                                if (k == spaces.length) {
                                    break;
                                }
                            }
                            otherSpaces[writeLoc++] = spaces[k];
                        }
                        int[] dir = netForceDirection(spaces[i], otherSpaces);
                        processMove(spaces[i], spaces[j], dir, otherSpaces);
                        doContinue = true;
                    }
                }
            }
            Arrays.stream(spaces).map((space)->space.getCenter()).forEach(System.out::println);
            System.out.println("----------");
        } while(doContinue);
    }
    
    private static void processMove(ISpace a, ISpace b, int[] dir, ISpace[] notA) {
        if (doesOverlap(a, b)) {
            if (a instanceof ICompositeSpace) {
                for(ISpace space : ((ICompositeSpace)a).getSpaces()) {
                    if (dir[0] < 0) {
                        double tlx = space.getTopLeft().getX();
                        if (tlx+BORDER_WIDTH < Math.abs(dir[0])) {
                            dir[0] = -1*(int)Math.max(tlx-BORDER_WIDTH,0);
                        }
                    }
                }
            } else if (dir[0] < 0) {
                double tlx = a.getTopLeft().getX();
                if (tlx+BORDER_WIDTH < Math.abs(dir[0])) {
                    dir[0] = -1*(int)Math.max(tlx-BORDER_WIDTH,0);
                }
            }
            if (a instanceof ICompositeSpace) {
                for(ISpace space : ((ICompositeSpace)a).getSpaces()) {
                    if (dir[1] < 0) {
                        double tly = space.getTopLeft().getY();
                        if (tly+BORDER_WIDTH < Math.abs(dir[1])) {
                            dir[1] = -1*(int)Math.max(tly-BORDER_WIDTH,0);
                        }
                    }
                }
            } else if (dir[1] < 0) {
                double tly = a.getTopLeft().getY();
                if (tly+BORDER_WIDTH < Math.abs(dir[1])) {
                    dir[1] = -1*(int)Math.max(tly-BORDER_WIDTH,0);
                }
            }
            if (a instanceof ICompositeSpace) {
                for(ISpace space : ((ICompositeSpace)a).getSpaces()) {
                    space.move(dir[0], dir[1]);
                }
            } else {
                a.move(dir[0], dir[1]);
            }
            int div = Math.min(Math.abs(dir[0]), Math.abs(dir[1]));
            if (div == 0) {
                div = Math.max(Math.abs(dir[0]), Math.abs(dir[1]));
            }
            for(int i = 0; i < 2; i ++) {
                if (dir[i] < 0) {
                    dir[i] = (int)Math.ceil(dir[i]/div) - 1;
                } else {
                    dir[i] = (int)Math.ceil(dir[i]/div);
                }
            }
            
            boolean keepMoving = false;
            do{
                keepMoving = false;
                for(ISpace space : notA) {
                    if (space instanceof ICompositeSpace) {
                        for(ISpace subspaceNotA : ((ICompositeSpace)space).getSpaces()) {
                            if (a instanceof ICompositeSpace) {
                                for(ISpace subspaceA : ((ICompositeSpace)a).getSpaces()) {
                                    if (subspaceA.getCenter().equals(subspaceNotA.getCenter())) {
                                        if (dir[0] < 0 && subspaceA.getTopLeft().getX()+BORDER_WIDTH < Math.abs(dir[0])) {
                                            dir[0] = -1*(int)Math.max(subspaceA.getTopLeft().getX()-BORDER_WIDTH,0);
                                        }
                                        if (dir[1] < 0 && subspaceA.getTopLeft().getY()+BORDER_WIDTH < Math.abs(dir[1])) {
                                            dir[1] = -1*(int)Math.max(subspaceA.getTopLeft().getY()-BORDER_WIDTH,0);
                                        }
                                        ((ICompositeSpace)a).move(dir[0], dir[1]);
                                        keepMoving = true;
                                        break;
                                    }
                                }
                            } else if (a.getCenter().equals(subspaceNotA.getCenter())) {
                                if (dir[0] < 0 && a.getTopLeft().getX()+BORDER_WIDTH < Math.abs(dir[0])) {
                                    dir[0] = -1*(int)Math.max(a.getTopLeft().getX()-BORDER_WIDTH,0);
                                }
                                if (dir[1] < 0 && a.getTopLeft().getY()+BORDER_WIDTH < Math.abs(dir[1])) {
                                    dir[1] = -1*(int)Math.max(a.getTopLeft().getY()-BORDER_WIDTH,0);
                                }
                                a.move(dir[0], dir[1]);
                                keepMoving = true;
                                break;
                            }
                        }
                    } else {
                        if (a instanceof ICompositeSpace) {
                            for(ISpace subspaceA : ((ICompositeSpace)a).getSpaces()) {
                                if (subspaceA.getCenter().equals(space.getCenter())) {
                                    if (dir[0] < 0 && subspaceA.getTopLeft().getX()+BORDER_WIDTH < Math.abs(dir[0])) {
                                        dir[0] = -1*(int)Math.max(a.getTopLeft().getX()-BORDER_WIDTH,0);
                                    }
                                    if (dir[1] < 0 && subspaceA.getTopLeft().getY()+BORDER_WIDTH < Math.abs(dir[1])) {
                                        dir[1] = -1*(int)Math.max(a.getTopLeft().getY()-BORDER_WIDTH,0);
                                    }
                                    ((ICompositeSpace)a).move(dir[0], dir[1]);
                                    keepMoving = true;
                                    break;
                                }
                            }
                        } else if (a.getCenter().equals(space.getCenter())) {
                            if (dir[0] < 0 && a.getTopLeft().getX()+BORDER_WIDTH < Math.abs(dir[0])) {
                                dir[0] = -1*(int)Math.max(a.getTopLeft().getX()-BORDER_WIDTH,0);
                            }
                            if (dir[1] < 0 && a.getTopLeft().getY()+BORDER_WIDTH < Math.abs(dir[1])) {
                                dir[1] = -1*(int)Math.max(a.getTopLeft().getY()-BORDER_WIDTH,0);
                            }
                            a.move(dir[0], dir[1]);
                            keepMoving = true;
                            break;
                        }
                    }
                }
            } while(keepMoving);
        }
    }
    
    private static boolean doesOverlap(ISpace a, ISpace b) {
        ISpace[] aArr;
        ISpace[] bArr;
        if (a instanceof ICompositeSpace) {
            aArr = ((ICompositeSpace)a).getSpaces();
        } else {
            aArr = new ISpace[]{a};
        }
        if (b instanceof ICompositeSpace) {
            bArr = ((ICompositeSpace)b).getSpaces();
        } else {
            bArr = new ISpace[]{b};
        }
        for(ISpace checkA : aArr) {
            for(ISpace checkB : bArr) {
                IPoint centerA = checkA.getCenter();
                IPoint centerB = checkB.getCenter();

                double xCenterGap = Math.abs(centerA.getX() - centerB.getX());
                double yCenterGap = Math.abs(centerA.getY() - centerB.getY());

                double xSpan = (checkA.getWidth() + checkB.getWidth() + BORDER_WIDTH*2) / 2.0;
                double ySpan = (checkA.getHeight() + checkB.getHeight() + BORDER_WIDTH*2) / 2.0;

                if ((xCenterGap < xSpan) && (yCenterGap < ySpan)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static int[] netForceDirection(ISpace space, ISpace[] rest) {
        double[] force = new double[]{0,0};
        int area = space.getHeight()*space.getWidth();
        if (space instanceof ICompositeSpace) {
            for(ISpace spaceSubSpace : ((ICompositeSpace)space).getSpaces()) {
                for(ISpace other : rest) {
                    if (other instanceof ICompositeSpace) {
                        ICompositeSpace cSpace = (ICompositeSpace)other;
                        for(ISpace subSpace : cSpace.getSpaces()) {
                            double dist = spaceSubSpace.getCenter().distanceTo(subSpace.getCenter());
                            double angle = spaceSubSpace.getCenter().angleTo(subSpace.getCenter());
                            int areaOther = subSpace.getHeight() * subSpace.getWidth();
                            double partial = REPULSION_FORCE;//*area*areaOther;
                            force[0] += Math.sin(angle)*partial/Math.pow(dist,2);
                            force[1] += Math.cos(angle)*partial/Math.pow(dist,2);
                        }
                    } else {
                        double dist = spaceSubSpace.getCenter().distanceTo(other.getCenter());
                        double angle = spaceSubSpace.getCenter().angleTo(other.getCenter());
                        int areaOther = other.getHeight() * other.getWidth();
                        double partial = REPULSION_FORCE;//*area*areaOther;
                        force[0] += Math.sin(angle)*partial/Math.pow(dist,2);
                        force[1] += Math.cos(angle)*partial/Math.pow(dist,2);
                    }
                }
            }
        } else {
            for(ISpace other : rest) {
                if (other instanceof ICompositeSpace) {
                    ICompositeSpace cSpace = (ICompositeSpace)other;
                    for(ISpace subSpace : cSpace.getSpaces()) {
                        double dist = space.getCenter().distanceTo(subSpace.getCenter());
                        double angle = space.getCenter().angleTo(subSpace.getCenter());
                        int areaOther = subSpace.getHeight() * subSpace.getWidth();
                        double partial = REPULSION_FORCE;//*area*areaOther;
                        force[0] += Math.sin(angle)*partial/Math.pow(dist,2);
                        force[1] += Math.cos(angle)*partial/Math.pow(dist,2);
                    }
                } else {
                    double dist = space.getCenter().distanceTo(other.getCenter());
                    double angle = space.getCenter().angleTo(other.getCenter());
                    int areaOther = other.getHeight() * other.getWidth();
                    double partial = REPULSION_FORCE;//*area*areaOther;
                    force[0] += Math.sin(angle)*partial/Math.pow(dist,2);
                    force[1] += Math.cos(angle)*partial/Math.pow(dist,2);
                }
            }
        }
        if (force[0] < 0) {
            force[0] = Math.ceil(force[0]) - 1;
        } else {
            force[0] = Math.ceil(force[0]);
        }
        if (force[1] < 0) {
            force[1] = Math.ceil(force[1]) - 1;
        } else {
            force[1] = Math.ceil(force[1]);
        }
        return new int[]{(int)force[0], (int)force[1]};
    }
}
